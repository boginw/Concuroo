package concuroo.nodes.statements;

import concuroo.factories.statement.BlockFactory;
import concuroo.factories.statement.StatementFactory;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.List;

/**
 * A BlockStatement is a representation of a block of statements.
 */
public class BlockStatement implements Statement {

  private final List<Statement> statements;
  private final String literal;
  private final SymbolTable symbolTable;

  /**
   * Default constructor
   *
   * @param literal The literal read
   */
  public BlockStatement(String literal) {
    statements = new ArrayList<>();
    symbolTable = new SymbolTable();
    this.literal = literal;
  }

  /**
   * Adds a statement to the block
   *
   * @param statement Statement to add
   */
  public void addStatement(Statement statement) {
    statements.add(statement);
  }

  /**
   * Gets all statements in block
   *
   * @return All statements in block
   */
  public List<Statement> getStatements() {
    return this.statements;
  }

  @Override
  public String getLiteral() {
    return literal;
  }

  @Override
  public int getVal() {
    for (Statement statement : statements) {
      statement.getVal();
    }

    return 0;
  }

  /**
   * Fetches the block's SymbolTable
   *
   * @return SymbolTable of the block
   */
  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  /**
   * Sets the block's SymbolTable
   *
   * @param symbolTable SymbolTable for the block
   */
  public void setSymbolTableParent(SymbolTable symbolTable) {
    this.symbolTable.setParent(symbolTable);
  }

  @Override
  public StatementFactory getFactory() {
    return new BlockFactory();
  }

}
