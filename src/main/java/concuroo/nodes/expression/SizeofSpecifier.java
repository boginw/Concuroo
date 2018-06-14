package concuroo.nodes.expression;

import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.Expression;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Node;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * This class represents an expression of the form sizeof(long int)
 */
public class SizeofSpecifier implements Expression, HasSpecifiers {

  private ReturnType returnReturnType;
  private DeclarationSpecifierList specifiers = new DeclarationSpecifierList();

  /**
   * Default constructor
   */
  public SizeofSpecifier() {
  }

  /**
   * Default constructor
   *
   * @param specifiers The specifiers to take the size of
   */
  public SizeofSpecifier(DeclarationSpecifierList specifiers) {
    setSpecifiers(specifiers);
  }

  @Override
  public String getLiteral() {
    return "sizeof" + "(" + getSpecifiers().getLiteral() + ")";
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    UnaryExpressionContext actx = Node.checkCtx(ctx, UnaryExpressionContext.class);

    List<ParseTree> operators = actx.declarationSpecifiers().children;

    for (ParseTree operator : operators) {
      getSpecifiers().add(operator.getText());
    }

    return this;
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
    return new ReturnType(){{
      type = Types.INT;
    }};
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a sizeof operation");
  }
}
