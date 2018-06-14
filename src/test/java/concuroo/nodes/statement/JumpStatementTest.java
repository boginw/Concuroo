package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;

public class JumpStatementTest {
  @Test
  public void shouldImplementStatement(){
    JumpStatement st = new JumpStatement() {
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