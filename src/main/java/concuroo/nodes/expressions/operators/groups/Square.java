package concuroo.nodes.expressions.operators.groups;

import concuroo.factories.expression.ExpressionFactory;
import concuroo.factories.expression.GroupFactory;
import concuroo.language.Associativity;

public class Square implements Group {

  private boolean isStart;
  private String literal;

  @Override
  public int getPrecedence() {
    return 1;
  }

  @Override
  public Associativity getAssociativity() {
    return Associativity.LeftToRight;
  }

  @Override
  public String getLiteral() {
    return literal;
  }

  @Override
  public void setLiteral(String literal) {
    this.literal = literal;
    isStart = literal.equals("[");
  }

  @Override
  public int getVal() {
    return 0;
  }

  @Override
  public boolean isStart() {
    return isStart;
  }

  @Override
  public void setStart(boolean start) {
    isStart = start;
  }

  @Override
  public ExpressionFactory getFactory() {
    return new GroupFactory<>(getLiteral().charAt(0), Square::new);

  }

}
