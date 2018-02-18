package concuroo.nodes.statements;

import java.util.ArrayList;
import java.util.List;

/**
 * A BlockStatement is a representation of a block of
 * statements.
 */
public class BlockStatement implements Statement{
    private final List<Statement> statements;

    /**
     * Default constructor
     */
    public BlockStatement(){
        statements = new ArrayList<>();
    }

    /**
     * Adds a statement to the block
     * @param statement Statement to add
     */
    public void addStatement(Statement statement){
        statements.add(statement);
    }

    /**
     * Gets all statements in block
     * @return All statements in block
     */
    public List<Statement> getStatements(){
        return this.statements;
    }

    @Override
    public String getLiteral() {
        // TODO: figure this out
        return null;
    }

    @Override
    public int getVal() {
        for (Statement statement: statements) {
            statement.getVal();
        }

        return 0;
    }
}
