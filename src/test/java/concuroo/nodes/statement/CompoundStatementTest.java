package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import org.junit.Test;

public class CompoundStatementTest {

  @Test
  public void addStatement() {
    CompoundStatement cs = new CompoundStatement();
    Statement st = () -> "cool statement bro";
    cs.addStatement(st);
    assertEquals(st, cs.getStatement(0));
  }

  @Test
  public void size() {
    CompoundStatement cs = new CompoundStatement();
    Statement st = () -> "cool statement bro";
    cs.addStatement(st);
    assertEquals(1, cs.size());
  }
}