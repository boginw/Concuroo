package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import org.junit.Test;

public class JumpStatementTest {
  @Test
  public void shouldImplementStatement(){
    JumpStatement st = new JumpStatement() {
      @Override
      public String getLiteral() {
        return null;
      }
    };
    assertTrue(st instanceof Statement);
  }
}