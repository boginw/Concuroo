package nodes;

import static org.junit.Assert.*;

import org.junit.Test;

public class IdentifierNodeTest {

  @Test
  public void setValue() {
    IdentifierNode node_one = new IdentifierNode();
    node_one.setValue(5);

    assertEquals(node_one.getValue, 5);
  }

  @Test
  public void equals() {
    IdentifierNode node_one = new IdentifierNode();
    node_one.setValue(5);

    IdentifierNode node_two = new IdentifierNode();
    node_two.setValue(5);


    IdentifierNode node_three = new IdentifierNode();
    node_two.setValue(6);

    assertTrue(node_one.equals(node_two));
    assertTrue(node_two.equals(node_one));
    assertFalse(node_one.equals(node_three));
  }
}