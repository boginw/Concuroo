package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.nodes.Expression;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;

public class SelectionStatementTest {
  @Test
  public void shouldExtendStatement() {
    SelectionStatement st = new SelectionStatement() {
      @Override
      public Expression getCondition() {
        return null;
      }

      @Override
      public void setCondition(Expression condition) {

      }

      @Override
      public Statement getConsequence() {
        return null;
      }

      @Override
      public void setConsequence(Statement consequence) {

      }

      @Override
      public String getLiteral() {
        return null;
      }

      @Override
      public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        return null;
      }
    };
    assertTrue(st instanceof Statement);
  }
}