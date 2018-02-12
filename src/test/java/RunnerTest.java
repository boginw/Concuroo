import junit.framework.*;
import org.junit.Test;


public class RunnerTest extends TestCase {

  @Test
  public void testAdd(){
    Runner printer = new Runner();
    java.lang.String result = printer.printed();
    assertTrue(result == "yes");
  }
}

