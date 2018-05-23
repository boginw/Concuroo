package concurooInterface;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class Exec {
  private static void pipeStream(InputStream input, OutputStream output) throws IOException {
    byte buffer[] = new byte[1024];
    int numRead;

    do {
      numRead = input.read(buffer);
      output.write(buffer, 0, numRead);
    } while (input.available() > 0);

    output.flush();
    output.close();
  }

  public static String process(String[] commands, String stdIn) throws IOException {
    InputStream stream = new ByteArrayInputStream(stdIn.getBytes(Charset.defaultCharset()));

    Process proc = Runtime.getRuntime().exec(commands);

    pipeStream(stream, proc.getOutputStream());

    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

    String s = null;
    StringBuilder sb = new StringBuilder();
    while ((s = stdInput.readLine()) != null) {
      sb.append(s).append("\n");
    }

    while ((s = stdError.readLine()) != null) {
      System.out.println(s);
    }

    return sb.toString();
  }
}

