package concuroo.nodes;

import ConcurooParser.ConcurooParser.DeclarationSpecifiersContext;
import concuroo.CSTVisitor;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * The main object that is stored in the abstract syntax tree
 */
public interface Node {

  /**
   * Fetches the literal of the node
   *
   * @return Literal of the node
   */
  String getLiteral();

  Node parse(ParserRuleContext ctx, CSTVisitor visitor);

  static <T extends ParserRuleContext> T checkCtx(ParserRuleContext ctx, Class<T> classObj) {
    if(!classObj.isInstance(ctx)) {
      throw new RuntimeException("Wrong context");
    }

    return (T) ctx;
  }

  static DeclarationSpecifierList parseDeclarationSpecifiers(ParseTree parseTree) {
    List<String> specifiers = new ArrayList<>();
    if (parseTree instanceof DeclarationSpecifiersContext) {
      DeclarationSpecifiersContext child = (DeclarationSpecifiersContext) parseTree;

      for (ParseTree specifier : child.children) {
        specifiers.add(specifier.getText());
      }

      return new DeclarationSpecifierList(specifiers);
    }
    return null;
  }
}
