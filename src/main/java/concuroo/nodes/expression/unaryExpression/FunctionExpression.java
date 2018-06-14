package concuroo.nodes.expression.unaryExpression;

import ConcurooParser.ConcurooParser.PostfixExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.nodes.ArgumentExpressionList;
import concuroo.nodes.Expression;
import concuroo.nodes.FunctionDeclaration;
import concuroo.nodes.Node;
import concuroo.nodes.expression.HasDefinition;
import concuroo.nodes.expression.Identifier;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form a(1,2,3), that is, a function call
 */
public class FunctionExpression implements Expression, Identifier,
    HasDefinition<FunctionDeclaration> {

  private ReturnType returnReturnType;
  private String identifier;
  private FunctionDeclaration definition;
  private ArgumentExpressionList parameters = new ArgumentExpressionList();

  /**
   * Default constructor
   */
  public FunctionExpression() {
  }

  /**
   * Default constructor
   *
   * @param identifier The identifier of the function call
   */
  public FunctionExpression(String identifier) {
    this.identifier = identifier;
  }

  /**
   * Sets the parameters of the function call
   *
   * @param parameters The parameters of the function call
   */
  public void setParameterList(ArgumentExpressionList parameters) {
    this.parameters = parameters;
  }

  /**
   * Gets the parameters of the function call
   *
   * @return The parameters of the function call
   */
  public ArgumentExpressionList getParameterList() {
    return parameters;
  }

  @Override
  public FunctionDeclaration getDefinition() {
    return definition;
  }

  @Override
  public void setDefinition(FunctionDeclaration definition) {
    this.definition = definition;
  }

  @Override
  public String getLiteral() {
    if (parameters.size() > 0) {
      return identifier + " " + parameters.getLiteral();
    }
    return identifier;
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    PostfixExpressionContext actx = Node.checkCtx(ctx, PostfixExpressionContext.class);

    setIdentifier(ctx.getChild(0).getText());

    if (actx.argumentExpressionList() != null) {
      setParameterList((ArgumentExpressionList) visitor.visit(actx.argumentExpressionList()));
    }

    return this;
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }

  @Override
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return identifier;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }
}
