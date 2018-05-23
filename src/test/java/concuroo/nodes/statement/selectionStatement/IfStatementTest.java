package concuroo.nodes.statement.selectionStatement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.binaryExpression.arithmeticBinaryExpression.AdditiveExpression;
import concuroo.nodes.statement.JumpStatement;
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
    Expression expr = new AdditiveExpression();
    st.setCondition(expr);

    assertEquals(expr, st.getCondition());
  }

  @Test
  public void setConsequence() {
    IfStatement st = new IfStatement();
    Statement stat = new JumpStatement() {
      @Override
      public String getLiteral() {
        return "const";
      }
    };
    st.setConsequence(stat);

    assertEquals(stat, st.getConsequence());
  }

  @Test
  public void setAlternative() {
    IfStatement st = new IfStatement();
    Statement stat = new JumpStatement() {
      @Override
      public String getLiteral() {
        return "alt";
      }
    };
    st.setAlternative(stat);

    assertEquals(stat, st.getAlternative());
  }
}