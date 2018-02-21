package concuroo.symbol;

import static org.junit.Assert.assertEquals;

import concuroo.nodes.EOF;
import org.junit.Test;

public class EOFTest {

  @Test
  public void literal() {
    EOF e = new EOF();
    assertEquals(e.getLiteral(), "EOF");
  }
}