import junit.framework.*;
import org.junit.Test;


public class RunnerTest extends TestCase {

  @Test
  public void testAdd(){
    Runner printer = new Runner();
    String result = printer.printed();
    assertTrue(result.equals("yes"));
  }
}
