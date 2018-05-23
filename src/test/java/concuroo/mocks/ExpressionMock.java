package concuroo.mocks;

import concuroo.ReturnType;
import concuroo.nodes.Expression;

public class ExpressionMock implements Expression {

  @Override
  public void setReturnType(ReturnType returnReturnType) {

  }

  @Override
  public ReturnType getReturnType() {
    return null;
  }

  @Override
  public String getLiteral() {
    return null;
  }
}
