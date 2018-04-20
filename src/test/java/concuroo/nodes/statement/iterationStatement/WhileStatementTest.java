package concuroo.nodes.statement.iterationStatement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import concuroo.nodes.expression.Expression;
import org.junit.Test;

public class WhileStatementTest {

  @Test
  public void setCondition() {
    WhileStatement wh = new WhileStatement();
    Expression expr = () -> "cool story bro";
    wh.setCondition(expr);
    assertEquals(expr, wh.getCondition());
  }

  @Test
  public void setConsequence() {
    WhileStatement wh = new WhileStatement();
    Statement stat = () -> "cool story bro";
    wh.setConsequence(stat);
    assertEquals(stat, wh.getConsequence());
  }
}