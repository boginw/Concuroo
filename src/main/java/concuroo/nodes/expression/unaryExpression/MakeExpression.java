package concuroo.nodes.expression.unaryExpression;

import ConcurooParser.ConcurooParser.InitializerContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Visitor;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Node;
import concuroo.nodes.Expression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the make expressions, which are of the form `make(chan int)` or something
 * similar
 */
public class MakeExpression implements Node, HasSpecifiers, Expression {

  private ReturnType returnReturnType;
  private DeclarationSpecifierList specifiers;

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return this.specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  @Override
  public String getLiteral() {
    return "make(" + specifiers.getLiteral() + ")";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    InitializerContext actx = Node.checkCtx(ctx, InitializerContext.class);

    setSpecifiers(Node.parseDeclarationSpecifiers(actx.declarationSpecifiers()));

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(getSpecifiers());
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

}
