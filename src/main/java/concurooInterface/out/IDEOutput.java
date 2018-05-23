package concurooInterface.out;

import concurooInterface.Out;
import java.io.IOException;

public class IDEOutput implements Out {

  @Override
  public void output(String program, String path) {
    new FileOutput().output(program, path);

    Runtime run  = Runtime.getRuntime();
    try {
      Process proc = run.exec(new String[] {"arduino", path});
    } catch (IOException e) {
      throw new RuntimeException("Arduino couldn't launch:" + e.getMessage());
    }
  }
}
