package concuroo.nodes;

import ConcurooParser.ConcurooParser.ArgumentExpressionListContext;
import ConcurooParser.ConcurooParser.AssignmentExpressionContext;
import concuroo.CSTVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
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

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    ArgumentExpressionListContext actx = Node.checkCtx(ctx, ArgumentExpressionListContext.class);

    Stack<AssignmentExpressionContext> expList = new Stack<>();

    while (actx != null) {
      if (actx.assignmentExpression() != null) {
        expList.push(actx.assignmentExpression());
        actx = actx.argumentExpressionList();
      }
    }

    while (!expList.empty()) {
      add((Expression) visitor.visit(expList.pop()));
    }

    return this;
  }
}