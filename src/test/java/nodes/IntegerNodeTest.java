package nodes;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerNodeTest {

  @Test
  public void getVal() {
    IntegerNode node_one = new IntegerNode("5");

    assertEquals(node_one.getVal(), 5);
  }

  @Test
  public void equals() {
    IntegerNode node_one = new IntegerNode("5");
    IntegerNode node_two = new IntegerNode("5");
    IntegerNode node_three = new IntegerNode("6");

    assertTrue(node_one.equals(node_two));
    assertTrue(node_two.equals(node_one));
    assertFalse(node_one.equals(node_three));
  }
}