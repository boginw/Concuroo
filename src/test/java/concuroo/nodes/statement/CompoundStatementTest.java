package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.CSTVisitor;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;

public class CompoundStatementTest {

  @Test
  public void addStatement() {
    CompoundStatement cs = new CompoundStatement();
    Statement st = new JumpStatement() {
      @Override
      public String getLiteral() {
        return null;
      }

      @Override
      public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        return null;
      }
    };
    cs.add(st);
    assertEquals(st, cs.get(0));
  }

  @Test
  public void size() {
    CompoundStatement cs = new CompoundStatement();
    Statement st = new JumpStatement() {
      @Override
      public String getLiteral() {
        return null;
      }

      @Override
      public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
        return null;
      }
    };
    cs.add(st);
    assertEquals(1, cs.size());
  }
}