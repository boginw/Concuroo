import com.google.devtools.common.options.OptionsParser;
import com.google.devtools.common.options.OptionsParser.HelpVerbosity;
import concurooInterface.CompilerOptions;
import concurooInterface.Pipe;
import concurooInterface.in.FileInput;
import concurooInterface.out.FileOutput;
import concurooInterface.out.IDEOutput;
import concurooInterface.out.InoOutput;
import concurooInterface.pipe.Compiler;
import concurooInterface.pipe.Pipeline;
import concurooInterface.pipe.Preprocessor;
import concurooInterface.pipe.Prettifier;
import java.util.Collections;

public class Runner {

  public static void main(String[] args) {

    OptionsParser parser = OptionsParser.newOptionsParser(CompilerOptions.class);
    parser.parseAndExitUponError(args);
    CompilerOptions options = parser.getOptions(CompilerOptions.class);

    // no options? then you need help my friend
    if (options == null) {
      printUsage(parser);
    } else if (options.file.isEmpty() || options.outfile.isEmpty()) {
      // no input? no output? you need help
      printUsage(parser);
    } else {
      // Create a pipe from the input file
      Pipe program = new FileInput().input(options.file).to(new Pipeline());

      // Need preprocessor?
      if (options.pre) {
        program = program.to(new Preprocessor());
      }

      // Compile the input
      program = program.to(new Compiler());

      // Prettify?
      if (options.pretty) {
        program = program.to(new Prettifier());
      }

      // Determine how to output the compiled result
      if (options.arduino) {
        program.out(new IDEOutput(), options.outfile);
      } else if (options.ino) {
        program.out(new InoOutput(), options.outfile);
      } else {
        program.out(new FileOutput(), options.outfile);
      }
    }
  }

  @SuppressWarnings("SameReturnValue")
  public String printed() {
    return "yes";
  }

  private static void printUsage(OptionsParser parser) {
    System.out.println("Usage: java -jar concuroo.jar OPTIONS");
    System.out.println(
        parser.describeOptions(
            Collections.emptyMap(),
            HelpVerbosity.MEDIUM
        )
    );
  }
}
