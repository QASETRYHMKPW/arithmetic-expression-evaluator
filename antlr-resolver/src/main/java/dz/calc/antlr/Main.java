package dz.calc.antlr;

import java.util.Scanner;

public class Main {
    private static final String EXIT_COMMAND = "exit!";
    public static void main(String[] args){
        System.out.println("Type exit! to quit the application.");
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();
        while (true){
            System.out.print("> ");
            String input = scanner.nextLine();
            if(EXIT_COMMAND.equalsIgnoreCase(input)){
                break;
            }

            try {
                Double result = calculator.calculate(input);

                System.out.printf("%s = %s%n", input, result);
            }catch (Exception ex){
                System.out.println(ex);
            }
        }

    }
}
