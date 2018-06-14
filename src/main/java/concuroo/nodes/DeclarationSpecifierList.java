package concuroo.nodes;

import concuroo.CSTVisitor;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.StringUtils;

/**
 * A list of specifiers, aka a list of strings
 */
public class DeclarationSpecifierList extends ArrayList<String> implements Node {

  public DeclarationSpecifierList() {}

  /**
   * Default constructor
   *
   * @param specifiers A list of specifiers
   */
  public DeclarationSpecifierList(List<String> specifiers) {
    super(specifiers);
  }

  @Override
  public String getLiteral() {
    return StringUtils.join(this, " ");
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    return this;
  }
}
