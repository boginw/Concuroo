package concurooInterface;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

@SuppressWarnings("unused")
public class CompilerOptions extends OptionsBase {

  @Option(
      name = "help",
      abbrev = 'h',
      help = "Prints usage info.",
      defaultValue = "true"
  )
  public boolean help;

  @Option(
      name = "output",
      abbrev = 'o',
      help = "The output file",
      defaultValue = ""
  )
  public String outfile;

  @Option(
      name = "pre",
      abbrev = 'p',
      help = "Preprocessor",
      defaultValue = "false"
  )
  public boolean pre;

  @Option(
      name = "repl",
      abbrev = 'r',
      help = "REPL",
      defaultValue = "false"
  )
  public boolean repl;

  @Option(
      name = "file",
      abbrev = 'f',
      help = "File to open",
      defaultValue = ""
  )
  public String file;

  @Option(
      name = "target",
      abbrev = 't',
      help = "Target language",
      defaultValue = "arduino"
  )
  public String target;

  @Option(
      name = "ide",
      help = "Open in arduino IDE",
      defaultValue = "false"
  )
  public boolean arduino;

  @Option(
      name = "ino",
      help = "Flash your arduino directly",
      defaultValue = "false"
  )
  public boolean ino;


  @Option(
      name = "pretty",
      abbrev = 'b',
      help = "Prettify the output",
      defaultValue = "false"
  )
  public boolean pretty;
}
