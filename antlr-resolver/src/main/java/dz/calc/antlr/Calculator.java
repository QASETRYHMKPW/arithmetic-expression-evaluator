package dz.calc.antlr;

import dz.calc.parser.ArithmeticEvaluatorParser;
import dz.calc.parser.ArithmeticEvaluatorLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Calculator {
    public double calculate(String expr){
        ArithmeticEvaluatorLexer lexer = new ArithmeticEvaluatorLexer(CharStreams.fromString(expr));
        ArithmeticEvaluatorParser parser = new ArithmeticEvaluatorParser(new CommonTokenStream(lexer));

        ParseTree pt = parser.start();

        ArithmeticEvaluateVisitor visitor = new ArithmeticEvaluateVisitor();
        return visitor.visit(pt);
    }
}
