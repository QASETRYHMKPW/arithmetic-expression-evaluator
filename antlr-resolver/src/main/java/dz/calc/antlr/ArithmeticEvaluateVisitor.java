package dz.calc.antlr;

import dz.calc.parser.ArithmeticEvaluatorParser;
import dz.calc.parser.ArithmeticEvaluatorBaseVisitor;
import org.apache.commons.math3.special.Gamma;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

public class ArithmeticEvaluateVisitor extends ArithmeticEvaluatorBaseVisitor<Double> {
    private static final Map<String, BinaryOperator<Double> > OP_MAP = new HashMap<>(){{
        put("+", (a, b) -> a + b);
        put("-", (a, b) -> a - b);
        put("*", (a, b) -> a * b);
        put("/", (a, b) -> a / b);
        put("%", (a, b) -> a % b);
        put("^", Math::pow);
    }};

    private static final Map<String, Double> ConstantMap = new HashMap<>(){{
        put("pi", Math.PI);
        put("e", Math.E);
    }};

    @Override
    public Double visitLNGRP(ArithmeticEvaluatorParser.LNGRPContext ctx) {
        return Math.log(visit(ctx.expression()));
    }

    @Override
    public Double visitOPERAND1GRP(ArithmeticEvaluatorParser.OPERAND1GRPContext ctx) {
        String op = ctx.op1().getText();
        Double left = visit(ctx.expression(0));
        Double right = visit(ctx.expression(1));

        return Calculate(op, left, right);
    }

    @Override
    public Double visitOPERAND2GRP(ArithmeticEvaluatorParser.OPERAND2GRPContext ctx) {
        String op = ctx.op2().getText();
        Double left = visit(ctx.expression(0));
        Double right = visit(ctx.expression(1));

        return Calculate(op, left, right);
    }

    @Override
    public Double visitUNARYGROUP(ArithmeticEvaluatorParser.UNARYGROUPContext ctx) {
        return - visit(ctx.expression());
    }

    @Override
    public Double visitPARENGRP(ArithmeticEvaluatorParser.PARENGRPContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double visitLOGGRP(ArithmeticEvaluatorParser.LOGGRPContext ctx) {
        Double base = visit(ctx.expression(0));
        Double value = visit(ctx.expression(1));
        return logxN(value, base);
    }

    @Override
    public Double visitNUMBERGRP(ArithmeticEvaluatorParser.NUMBERGRPContext ctx) {
        String text = ctx.getText();
        if(ConstantMap.containsKey(text)){
            return ConstantMap.get(text);
        }
        return Double.parseDouble(ctx.NUM().getText());
    }

    @Override
    public Double visitSQRTGRP(ArithmeticEvaluatorParser.SQRTGRPContext ctx) {
        return Math.sqrt(visit(ctx.expression()));
    }

    @Override
    public Double visitROOTGRP(ArithmeticEvaluatorParser.ROOTGRPContext ctx) {
        Double power = visit(ctx.expression(0));
        Double value = visit(ctx.expression(1));
        return Math.pow(value, 1.0/power);
    }

    @Override
    public Double visitFACTGRP(ArithmeticEvaluatorParser.FACTGRPContext ctx) {
        return Gamma.gamma(visit(ctx.expression()) + 1);
    }

    private double Calculate(final String operator, double left, double right){
        var op = OP_MAP.get(operator);
        return op.apply(left, right);
    }

    private static double logxN(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}
