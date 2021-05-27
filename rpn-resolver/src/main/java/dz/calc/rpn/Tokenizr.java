package dz.calc.rpn;

import org.javatuples.Triplet;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Tokenizr {
    private final static List<Triplet<Token.TokenType, Integer, String>> TOKEN_REGEX_LIST= Arrays.asList(
            //           Token Type, Capture group,  Regular expression
            Triplet.with(Token.TokenType.Operand,  1, "^(-\\d+)"),
            Triplet.with(Token.TokenType.Operand,  2, "\\((-\\d+)\\)"),
            Triplet.with(Token.TokenType.Operand,  3, "(\\d+)"),
            Triplet.with(Token.TokenType.Operator, 4, "([\\+\\-\\*\\/])"),
            Triplet.with(Token.TokenType.Brace,    5, "([\\(\\)])")
    );

    private final static Map<Integer, Token.TokenType> CAPTURE_GROUP_TOKEN_TYPE_MAP =
            TOKEN_REGEX_LIST.stream().collect(Collectors.toMap(Triplet::getValue1, Triplet::getValue0));
    private final static String TOKEN_REGEX =
            TOKEN_REGEX_LIST.stream().map(p->p.getValue2()).reduce((a,b)->String.format("%s|%s", a, b)).get();

    public List<Token> Tokenize(String expression){
        List<Token> ret = new ArrayList<>();
        Matcher matcher = Pattern.compile(TOKEN_REGEX).matcher(expression);
        while(matcher.find()){
            var token = findGroupFrom1(matcher);
            if(token == null){
                throw new IllegalStateException("Nothing captured from regular expression. " +
                        "Ensure the regular expression have at least one capture group");
            }
            ret.add(token);
        }
        return ret;
    }

    private static Token findGroupFrom1(Matcher matcher){
        for(int i = 1; i <= matcher.groupCount(); i++){
            var groupMatch = matcher.group(i);
            if (groupMatch != null){
                return new Token(CAPTURE_GROUP_TOKEN_TYPE_MAP.get(i), groupMatch);
            }
        }
        return null;
    }
}
