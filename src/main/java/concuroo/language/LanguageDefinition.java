package concuroo.language;

import concuroo.nodes.expressions.Expression;
import concuroo.nodes.statements.Statement;

/**
 * Extend this class to create your grammar
 */
public abstract class LanguageDefinition {
    private final LG g;

    /**
     * Default constructor
     */
    public LanguageDefinition(){
        this.g = new LG();
        registerTokens(g);
        registerStatements(g);
    }

    /**
     * Registers a token to the grammar
     * @param g Language grammar
     */
    protected abstract void registerTokens(LG g);

    /**
     * Registers a statement to the grammar
     * @param g Language grammar
     */
    protected abstract void registerStatements(LG g);

    /**
     * Gets the defined grammar
     * @return Language grammar
     */
    public LG getGrammar(){
        return g;
    }

    /**
     * Used in statement definitions to denote an expression
     * @return Expression class type
     */
    protected final Class<Expression> expr(){
        return Expression.class;
    }

    /**
     * Used in statement definitions to denote a statement
     * @return Statement class type
     */
    protected final Class<Statement> stat(){
        return Statement.class;
    }

    /**
     * Used in statement definitions to denote a multiple
     * of either statements or expressions
     * @param t Multiple of
     * @return Array with t inside.
     */
    protected final Class<?>[] multi(Class<?> t){
        return new Class<?>[]{t};
    }
}
