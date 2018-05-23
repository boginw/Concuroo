package concurooInterface.out;

import concurooInterface.Out;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutput implements Out {

  @Override
  public void output(String program, String path) {
    try {
      writeToFile(path, program);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Writes a string to a file, creates the file if it doesn't exist
   * @param path Path to the file to write to
   * @param toWrite What to write to the file
   * @throws IOException If the file couldn't be written to
   */
  private static void writeToFile(String path, String toWrite) throws IOException {
    File file = new File(path);
    boolean unused = file.createNewFile();
    FileOutputStream oFile = new FileOutputStream(file, false);
    oFile.write(toWrite.getBytes());
  }
}
