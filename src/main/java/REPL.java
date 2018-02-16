import lexer.Lexer;
import nodes.Node;
import parser.Parser;
import language.LG;
import symbol.SymbolTable;

import java.util.Scanner;

public class REPL {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void start(){
        SymbolTable st = new SymbolTable();
        LG lg = new ConcurooDefinition().getGrammar();
        Lexer l = new Lexer(st, lg);

        while(true) {
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            Node[] tokens = l.lex(line);

            try {
                String result = String.valueOf(Parser.AST(line, st, tokens, lg).getVal());
                System.out.println(result);
            } catch (Exception e) {
                System.out.println();
                e.printStackTrace();
            }
        }
    }
}
