package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import org.junit.Test;

public class JumpStatementTest {
  @Test
  public void shouldImplementStatement(){
    JumpStatement st = () -> "jump";
    assertTrue(st instanceof Statement);
  }
}