package concurooInterface.pipe;

import concurooInterface.Exec;
import concurooInterface.Out;
import concurooInterface.Pipe;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Prettifier implements Pipe {

  private String program;

  @Override
  public Pipe pipe(String program) {
    this.program = program;
    return this;
  }

  @Override
  public void out(Out output, String path) {
    output.output(build(), path);
  }

  @Override
  public String build() {
    try {
      String styleFile = Paths.get(
          new File("").toString(),
          "style_configuration", "output.cfg"
      ).toAbsolutePath().toString();

      return Exec.process(new String[]{"uncrustify", "-lcpp", "-c", styleFile}, program);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Pipe to(Pipe pipe) {
    return pipe.pipe(build());
  }
}
