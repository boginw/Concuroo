package concuroo.nodes.expression;

import concuroo.ReturnType;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public class InitializerList extends ArrayList<Node> implements Expression {

  private ReturnType returnReturnType;

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public String getLiteral() {
    return "{ " + StringUtils.join(this, ", ") + " }";
  }

}
