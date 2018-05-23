package concurooInterface.out;

import concurooInterface.Out;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class InoOutput implements Out {

  @Override
  public void output(String program, String path) {
    String env = Paths.get(path, "out").toString();
    String folder = Paths.get(env, "src").toString();
    boolean ignore = new File(folder).mkdirs();
    new FileOutput().output(program, Paths.get(folder, "sketch.ino").toString());

    exec(env, "ino build -m mega2560");
    exec(env, "ino upload -m mega2560");
  }

  /**
   * Executes a command, with a given environment
   * @param env Environment to execute from
   * @param command The command to be executed
   */
  private static void exec(String env, String command) {
    Runtime run  = Runtime.getRuntime();

    try {
      Process proc = run.exec(command, null, new File(env));

      BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

      String s;
      while ((s = stdInput.readLine()) != null) {
        System.out.println(s);
      }

      while ((s = stdError.readLine()) != null) {
        System.out.println(s);
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not start Ino: " + e.getMessage());
    }
  }
}
