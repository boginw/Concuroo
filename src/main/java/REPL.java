import lexer.Lexer;
import parser.Parser;
import symbol.Symbol;
import symbol.SymbolTable;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class REPL {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void start(){
        SymbolTable st = new SymbolTable();
        Lexer l = new Lexer(st);

        while(true) {
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            Symbol[] symbols = l.lex(line);

            try {
                String result = String.valueOf(Parser.AST(line, st, symbols).getVal());
                System.out.println(result);
            } catch (Exception e) {
                System.out.println();
                e.printStackTrace();
            }
        }
    }
}
