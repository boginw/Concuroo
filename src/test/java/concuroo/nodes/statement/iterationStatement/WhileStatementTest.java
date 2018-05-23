package concuroo.nodes.statement.iterationStatement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.literalExpression.BoolLiteral;
import concuroo.nodes.statement.jumpStatement.ReturnStatement;
import concuroo.nodes.statement.selectionStatement.WhileStatement;
import org.junit.Test;

public class WhileStatementTest {

  @Test
  public void setCondition() {
    WhileStatement wh = new WhileStatement();
    Expression expr = new BoolLiteral(true);
    wh.setCondition(expr);
    assertEquals(expr, wh.getCondition());
  }

  @Test
  public void setConsequence() {
    WhileStatement wh = new WhileStatement();
    Statement stat = new ReturnStatement();
    wh.setConsequence(stat);
    assertEquals(stat, wh.getConsequence());
  }
}