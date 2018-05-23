package concuroo.nodes.expression.lhsExpression;

import concuroo.ReturnType;
import concuroo.nodes.Expression;

import concuroo.nodes.expression.LHSExpression;
import java.util.ArrayList;

public class InitializerList implements LHSExpression {
  private ReturnType returnReturnType;
  private ArrayList<Expression> initializers = new ArrayList<>();

  public void addInitializer(Expression initializer) {
    initializers.add(initializer);
  }

  public void setInitializers(ArrayList<Expression> initializers) {
    this.initializers = initializers;
  }

  public Expression getInitializer(int index) {
    return initializers.get(index);
  }

  public ArrayList<Expression> getInitializers() {
    return initializers;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  //TODO: Implement this.
  @Override
  public String getLiteral() {
    return null;
  }
}
