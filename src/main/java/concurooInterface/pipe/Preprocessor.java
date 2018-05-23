package concurooInterface.pipe;

import concurooInterface.Exec;
import concurooInterface.Out;
import concurooInterface.Pipe;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Preprocessor implements Pipe {

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
      return Exec.process(new String[]{"gcc", "-xc", "-E", "-P", "-o-", "-"}, program);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Pipe to(Pipe pipe) {
    return pipe.pipe(build());
  }
}