package dz.calc.rpn;

import java.util.*;

class RPNConverter {
    private final static Map<String, Integer> PRECEDENCE = new HashMap<>(){{
        put("/", 5);
        put("*", 5);
        put("+", 4);
        put("-", 4);
        put("(", 1);
        put("#", 0);
    }};

    public Queue<Token> ConvertToRPN(List<Token> tokens){
        Queue<Token> q = new LinkedList<>();
        Stack<Token> s = new Stack<>();
        s.push(new Token(Token.TokenType.PlaceHolder, "#"));
        Token.TokenType prevTokenType = null;
        int obrace_count = 0, cbrace_count = 0;
        for(Token token : tokens){
            switch(token.type){
                case Brace:
                    if(token.value.equals("(")) {
                        obrace_count++;
                        s.push(token);
                    }else {
                        cbrace_count++;
                        while (!s.isEmpty() && !s.peek().value.equals("(")) {
                            q.add(s.pop());
                        }
                        if(!s.isEmpty())
                            s.pop(); // remove '(' from s
                    }
                    break;
                case Operator:
                    if (token.type == prevTokenType){
                        throw new IllegalArgumentException("Duplicate operators");
                    }
                    while(PRECEDENCE.get(token.value) <= PRECEDENCE.get(s.peek().value)){
                        q.add(s.pop());
                    }
                    s.push(token);
                    break;
                case Operand:
                    if (token.type.equals(prevTokenType)){
                        throw new IllegalArgumentException("Duplicate operands");
                    }
                    q.add(token);
                    break;
                default:
                    break;
            }
            prevTokenType = token.type;
        }

        while(!s.isEmpty()){
            q.add(s.pop());
        }

        if (obrace_count!= cbrace_count){
            throw new IllegalArgumentException("Brace not match");
        }

        return q;
    }
}
