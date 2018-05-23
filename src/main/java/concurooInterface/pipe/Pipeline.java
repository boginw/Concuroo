package concurooInterface.pipe;

import concurooInterface.Out;
import concurooInterface.Pipe;

public class Pipeline implements Pipe {
  private String program;

  @Override
  public Pipe pipe(String program) {
    this.program = program;
    return this;
  }

  @Override
  public String build() {
    return program;
  }

  @Override
  public void out(Out output, String path) {
    output.output(build(), path);
  }

  @Override
  public Pipe to(Pipe pipe) {
    return pipe.pipe(program);
  }
}
