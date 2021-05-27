package dz.calc.rpn;

public class Main {

    public static void main(String[] args){
        calc("-5+4-6");
        calc("437*32-4*(37-66 * 7)");
        calc("782/8+5*55-88*5/9");
        calc("437*32-4*(37-66 * 7/8*4)");
        calc("5-(-5)");
        calc("8--5");
        calc("8-((2*3)");
        calc("8-((2*3)))");
        calc("8-((2*3))");
        calc("8-((2*33 3))");
    }

    private static void calc(String expression) {
        System.out.println("Evaluating expression: " + expression);
        try {
            var tokenizr = new Tokenizr();
            var converter = new RPNConverter();
            var resolver = new RPNResolver();

            var tokens = tokenizr.Tokenize(expression);
            System.out.println("TOKENS: " + tokens);
            var rpn = converter.ConvertToRPN(tokens);
            System.out.println("   RPN: " + rpn);
            System.out.println(resolver.Resolve(rpn));
        }catch(Exception ex){
            System.out.println(ex);
        }

        System.out.println("-----------------------------------");
    }
}
