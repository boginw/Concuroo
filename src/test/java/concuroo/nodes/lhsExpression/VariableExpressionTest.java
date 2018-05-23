package concuroo.nodes.lhsExpression;

import static org.junit.Assert.*;

import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.lhsExpression.VariableExpression;
import concuroo.nodes.statement.VariableDeclaration;
import concuroo.symbol.SymbolTable;
import org.junit.Before;
import org.junit.Test;

public class VariableExpressionTest {

  private SymbolTable st = new SymbolTable();

  @Before
  public void setup(){
    VariableDeclaration vd1 = new VariableDeclaration();
    vd1.setIdentifier("literal");
    VariableDeclaration vd2 = new VariableDeclaration();
    vd2.setIdentifier("foo");
    st.insert(vd1);
    st.insert(vd2);
  }


  @Test
  public void shouldExist() {
    VariableExpression subject = new VariableExpression("literal", (VariableDeclaration) st.lookup("literal"));
    assertNotNull(subject);
  }

  @Test
  public void shouldImplementLHSExpressionInterface() {
    VariableExpression subject = new VariableExpression("literal", (VariableDeclaration) st.lookup("literal"));
    assertTrue(subject instanceof LHSExpression);
  }

  @Test
  public void shouldReturnCorrectLiteral() {
    VariableExpression subject = new VariableExpression("foo", (VariableDeclaration) st.lookup("foo"));
    assertEquals("foo", subject.getLiteral());
  }

}
