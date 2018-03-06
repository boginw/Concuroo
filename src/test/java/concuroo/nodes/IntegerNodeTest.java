package concuroo.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import concuroo.nodes.expressions.atoms.IntegerNode;
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

    assertTrue(node_one.getVal() == node_two.getVal());
    assertTrue(node_two.getLiteral().equals(node_one.getLiteral()));
    assertFalse(node_one.getVal() == node_three.getVal());
  }
}