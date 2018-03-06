import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class RunnerTest {

  @Test
  public void testAdd() {
    Runner printer = new Runner();
    String result = printer.printed();
    assertTrue(result.equals("yes"));
  }
}
