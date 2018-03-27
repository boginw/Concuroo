package concuroo;

import CuncurroParser.ConcurroParser;
import concuroo.language.LG;
import concuroo.lexer.Lexer;
import concuroo.nodes.statements.Statement;
import concuroo.parser.Parser;
import concuroo.symbol.SymbolTable;
import java.util.Scanner;

public class REPL {

  @SuppressWarnings("InfiniteLoopStatement")
  public static void start() {
    SymbolTable st = new SymbolTable();
    LG lg = new ConcurooDefinition().getGrammar();
    Lexer l = new Lexer(st, lg);
    Parser p = new Parser(l, st);

    while (true) {
      System.out.print(">> ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine();
      l.reset(line);

      try {
        Statement n = p.parseStatement();

        // Empty the remaining EOF in the parser's mRead
        p.consume();

        System.out.println(n.toString());
      } catch (Exception e) {
        System.out.println();
        e.printStackTrace();
      }
    }
  }
}
