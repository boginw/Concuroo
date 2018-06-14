package concuroo.nodes;

import ConcurooParser.ConcurooParser.StartContext;
import ConcurooParser.ConcurooParser.TranslationUnitContext;
import concuroo.CSTVisitor;
import java.util.ArrayList;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents a program. A program is just a list of declarations. Therefore, we just
 * extend the ArrayList class
 */
public class Program extends ArrayList<Declaration> implements Node {

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for (Declaration node : this) {
      sb.append(node.getLiteral());
    }
    return sb.toString();
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    StartContext actx = Node.checkCtx(ctx, StartContext.class);

    TranslationUnitContext tuc = actx.translationUnit();
    Stack<TranslationUnitContext> tucList = new Stack<>();
    while (tuc != null) {
      tucList.push(tuc);
      tuc = tuc.translationUnit();
    }
    while (!tucList.empty()) {
      add((Declaration) visitor.visit(tucList.pop().externalDeclaration()));
    }

    return this;
  }
}
