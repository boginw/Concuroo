package concuroo.nodes.expression.unaryExpression;

import ConcurooParser.ConcurooParser;
import ConcurooParser.ConcurooParser.CastExpressionContext;
import concuroo.CSTVisitor;
import concuroo.types.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.UnaryExpression;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form (int) 2.4, that is, casting an operand to a diff-
 * erent type
 */
public class CastExpression implements UnaryExpression, HasSpecifiers {
  private ReturnType returnReturnType;
  private Expression firstOperand;
  private DeclarationSpecifierList specifiers;

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  @Override
  public String getOperator() {
    return specifiers.getLiteral();
  }

  @Override
  public String getLiteral() {
    return "(" + specifiers.getLiteral() + ") " + getFirstOperand().getLiteral();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    CastExpressionContext actx = Node.checkCtx(ctx, CastExpressionContext.class);

    if (ctx.getChild(0) instanceof ConcurooParser.UnaryExpressionContext) {
      return visitor.visitUnaryExpression(actx.unaryExpression());
    }

    List<String> stringList = new ArrayList<>();
    stringList.add(ctx.getChild(1).getText());

    DeclarationSpecifierList decList = new DeclarationSpecifierList(stringList);

    setSpecifiers(decList);
    setFirstOperand((Expression) visitor.visit(ctx.getChild(3)));

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getFirstOperand());
  }

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
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