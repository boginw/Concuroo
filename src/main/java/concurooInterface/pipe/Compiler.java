package concurooInterface.pipe;

import ConcurooParser.ConcurooLexer;
import ConcurooParser.ConcurooParser;
import concuroo.ASTVisitor;
import concuroo.Builder;
import concuroo.CSTVisitor;
import concuroo.CodeGenerator;
import concuroo.Stdlib;
import concuroo.generators.ArduinoCodeGenerator;
import concuroo.nodes.Node;
import concuroo.nodes.Program;
import concuroo.symbol.SymbolTable;
import concurooInterface.Out;
import concurooInterface.Pipe;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Compiler implements Pipe {
  private String program;

  @Override
  public Pipe pipe(String program) {
    this.program = program;
    return this;
  }

  @Override
  public String build() {
    return compile(program).getOutput();
  }

  @Override
  public void out(Out output, String path) {
    output.output(build(), path);
  }

  @Override
  public Pipe to(Pipe pipe) {
    return pipe.pipe(build());
  }

  /**
   * Compiles the input
   * @param input The input to compile
   * @param generator The generator that generates the code
   * @return A builder
   */
  private static Builder compile(String input, CodeGenerator generator) {
    ConcurooLexer lex = new ConcurooLexer(CharStreams.fromString(input));
    ConcurooParser parser = new ConcurooParser(new CommonTokenStream(lex));
    parser.setBuildParseTree(true);

    SymbolTable st = new SymbolTable();

    Stdlib.addTo(st);

    ConcurooParser.StartContext ctx = parser.start();
    Node n = new CSTVisitor(st).visitStart(ctx);

    ASTVisitor ast = new ASTVisitor(st);
    ast.visit((Program)n);

    generator.visit((Program) n);
    return generator.getBuilder();
  }

  /**
   * Compiles the input with the default Arduino Code Generator
   * @param input The input to compile
   * @return A builder
   */
  private static Builder compile(String input) {
    return compile(input, new ArduinoCodeGenerator());
  }


}
