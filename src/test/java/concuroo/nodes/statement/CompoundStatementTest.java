package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
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
    };
    cs.add(st);
    assertEquals(1, cs.size());
  }
}