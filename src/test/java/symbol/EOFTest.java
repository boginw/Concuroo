package symbol;

import static org.junit.Assert.*;

import org.junit.Test;

public class EOFTest {

  @Test
  public void equals() {
    EOF e = new EOF();
    EOF f = new EOF();

    assertTrue(e.equals(f));
  }

  @Test
  public void literal() {
    EOF e = new EOF();

    assertEquals(e.literal, "\0");
  }
}