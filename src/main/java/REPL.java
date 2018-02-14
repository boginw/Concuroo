import parser.Parser;

import java.util.Scanner;

public class REPL {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void start(){
        while(true) {
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            try {
                String result = String.valueOf(Parser.AST(line).getVal());
                System.out.println(result);
            } catch (Exception e) {
                System.out.println();
                e.printStackTrace();
            }
        }
    }
}
