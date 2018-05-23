package concuroo.nodes;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * This class represents mainly parameters to function calls. It's a list of expressions, seperated
 * by commas.
 */
public class ArgumentExpressionList extends ArrayList<Expression> implements Node {

  @Override
  public String getLiteral() {
    List<String> params = new ArrayList<>();
    for (Node param : this) {
      params.add(param.getLiteral());
    }

    return StringUtils.join(params, ", ");
  }
}