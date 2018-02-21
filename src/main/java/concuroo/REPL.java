package concuroo;

import concuroo.language.LG;
import concuroo.lexer.Lexer;
import concuroo.nodes.Node;
import concuroo.parser.Parser;
import concuroo.symbol.SymbolTable;
import java.util.Scanner;

public class REPL {

  @SuppressWarnings("InfiniteLoopStatement")
  public static void start() {
    SymbolTable st = new SymbolTable();
    LG lg = new ConcurooDefinition().getGrammar();
    Lexer l = new Lexer(st, lg);

    while (true) {
      System.out.print(">> ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine();
      Node[] tokens = l.lex(line);

      try {
        String result = String.valueOf(Parser.AST(tokens, lg, st).getVal());
        System.out.println(result);
      } catch (Exception e) {
        System.out.println();
        e.printStackTrace();
      }
    }
  }
}
