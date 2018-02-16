package language;

public abstract class LanguageDefinition {
    private LG g;

    public LanguageDefinition(){
        this.g = new LG();
        registerTokens(g);
        registerStatements(g);
    }

    public abstract void registerTokens(LG g);
    public abstract void registerStatements(LG g);

    public LG getGrammar(){
        return g;
    }
}
