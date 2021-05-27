package dz.calc.rpn;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BinaryOperator;

class RPNResolver {
    public static final Map<String, BinaryOperator<Double> > OP_MAP = new HashMap<>(){{
        put("+", (a, b) -> a + b);
        put("-", (a, b) -> a - b);
        put("*", (a, b) -> a * b);
        put("/", (a, b) -> a / b);
    }};

    public double Resolve(Queue<Token> RPN){
        var result = 0.0;
        Stack<Double> s = new Stack<>();
        while(!RPN.isEmpty()){
            var token = RPN.remove();
            switch(token.type){
                case Operator:
                    var number2 = s.pop();
                    var number1 = s.pop();
                    result = OP_MAP.get(token.value).apply(number1, number2);
                    s.push(result);
                case PlaceHolder:
                    break;
                case Operand:
                    s.push(Double.parseDouble(token.value));
                    break;
            }
        }
        return s.peek();
    }
}
