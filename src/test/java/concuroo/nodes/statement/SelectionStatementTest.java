package concuroo.nodes.statement;

import static org.junit.Assert.*;

import concuroo.nodes.Statement;
import org.junit.Test;

public class SelectionStatementTest {
  @Test
  public void shouldExtendStatement() {
    SelectionStatement st = () -> "statement";
    assertTrue(st instanceof Statement);
  }
}