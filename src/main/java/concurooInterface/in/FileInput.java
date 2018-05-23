package concurooInterface.in;

import concurooInterface.In;
import concurooInterface.Pipe;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInput implements In {
  private String program;

  @Override
  public In input(String path) {
    program = readFile(path, Charset.defaultCharset());
    return this;
  }

  @Override
  public Pipe to(Pipe pipe) {
    return pipe.pipe(program);
  }

  private static String readFile(String path, Charset encoding) {
    byte[] encoded;
    try {
      encoded = Files.readAllBytes(Paths.get(path));
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    return new String(encoded, encoding);
  }
}
