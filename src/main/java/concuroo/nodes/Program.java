package concuroo.nodes;

public class Program implements Node {

  private TranslationUnit translationUnits = new TranslationUnit();

  @Override
  public String getLiteral() {
    return translationUnits.getLiteral();
  }

  public void setTranslationUnit(TranslationUnit ctx) {
    this.translationUnits = ctx;
  }
}
