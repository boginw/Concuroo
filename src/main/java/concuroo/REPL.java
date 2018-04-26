package concuroo;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.StartContext;
import concuroo.nodes.Node;
import concuroo.symbol.SymbolTable;
import java.util.Scanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class REPL {

  @SuppressWarnings("InfiniteLoopStatement")
  public static void start() {

    while (true) {
      System.out.print(">> ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine();

      try {
        ConcurooLexer lex = new ConcurooLexer(CharStreams.fromString(line));
        ConcurooParser parser = new ConcurooParser(new CommonTokenStream(lex));
        parser.setBuildParseTree(true);

        SymbolTable st = new SymbolTable();

        StartContext ctx = parser.start();
        Node n = new ASTVisitor(st).visit(ctx);

        System.out.print(n.getLiteral() + "\n");
      } catch (Exception e) {
        if (e.getMessage() != null) {
          System.err.println(e.getMessage());
        } else {
          e.printStackTrace();
        }
      }
    }
  }
}
