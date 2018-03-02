package concuroo.symbol;

import static org.junit.Assert.*;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.atoms.IdentifierNode;
import org.junit.Before;
import org.junit.Test;

public class SymbolTableTest {

  private static SymbolTable st;

  @Before
  public void beforeEach(){
    st = new SymbolTable();
  }

  @Test
  public void shouldNotFindAnythingInNewTable(){
    Node n = st.lookup("a");
    assertEquals(null, n);
  }

  @Test
  public void shouldFindAfterInserting(){
    IdentifierNode expect = new IdentifierNode("a");
    st.insert(expect);
    Node actual = st.lookup("a");
    assertEquals(expect, actual);
  }

  @Test
  public void valueShouldPersistAfterChange(){
    IdentifierNode expect = new IdentifierNode("a");
    expect.setValue(42);

    st.insert(expect);
    expect.setValue(69);

    Node actual = st.lookup("a");
    assertEquals(expect.getVal(), actual.getVal());
  }

  @Test
  public void shouldNotFindDueToNoParent(){
    SymbolTable parent = new SymbolTable(null);
    Node notExpected = new IdentifierNode("a");

    parent.insert(notExpected);
    Node actual = st.lookup("a");

    assertNotEquals(notExpected, actual);
  }

  @Test
  public void shouldFindInParent(){
    SymbolTable parent = new SymbolTable(null);
    Node expected = new IdentifierNode("a");
    parent.insert(expected);

    st.setParent(parent);
    Node actual = st.lookup("a");

    assertEquals(expected, actual);
  }
}