package nodes;

import static org.junit.Assert.*;

import nodes.expressions.atoms.IdentifierNode;
import org.junit.Test;

public class IdentifierNodeTest {

  @Test
  public void setValue() {
    IdentifierNode node_one = new IdentifierNode("a");
    node_one.setValue(5);

    assertEquals(node_one.getVal(), 5);
  }

  @Test
  public void equals() {
      IdentifierNode node_one = new IdentifierNode("a");
      node_one.setValue(5);

      IdentifierNode node_two = new IdentifierNode("b");
      node_two.setValue(5);

      IdentifierNode node_three = new IdentifierNode("c");
      node_three.setValue(6);

      assertTrue(node_one.getVal() == node_two.getVal());
      assertFalse(node_one.getVal() == node_three.getVal());
  }
}