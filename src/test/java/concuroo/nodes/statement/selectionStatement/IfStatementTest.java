package concuroo.nodes.statement.selectionStatement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.statement.SelectionStatement;
import org.junit.Test;

public class IfStatementTest {

  @Test
  public void shouldImplementSelectionStatement(){
    IfStatement st = new IfStatement();
    assertTrue(st instanceof SelectionStatement);
  }

  @Test
  public void setCondition() {
    IfStatement st = new IfStatement();
    Expression expr = () -> "expr";
    st.setCondition(expr);

    assertEquals(expr, st.getCondition());
  }

  @Test
  public void setConsequence() {
    IfStatement st = new IfStatement();
    Statement stat = () -> "cons";
    st.setConsequence(stat);

    assertEquals(stat, st.getConsequence());
  }

  @Test
  public void setAlternative() {
    IfStatement st = new IfStatement();
    Statement stat = () -> "alt";
    st.setAlternative(stat);

    assertEquals(stat, st.getAlternative());
  }
}