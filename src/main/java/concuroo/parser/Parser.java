package concuroo.parser;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.binary.BinaryOperator;
import concuroo.nodes.expressions.operators.indecisive.Indecisive;
import concuroo.nodes.statements.ExpressionStatement;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

  // Needs to be max (and not 0), because we chose to have reverse precedence, so lower is higher
  private final int initialPrecedence = Integer.MAX_VALUE;
  private final Iterator<Node> tokens;
  private final List<Node> mRead;
  private SymbolTable symbolTable;

  /**
   * Default constructor
   *
   * @param tokens Iterator of tokens
   * @param st SymbolTable
   */
  public Parser(Iterator<Node> tokens, SymbolTable st) {
    this.tokens = tokens;
    this.mRead = new ArrayList<>();
    this.symbolTable = st;
  }

  public Statement parseStatement() {
    Node token = lookAhead(0);

    if (token instanceof Statement) {
      token = consume();
      Statement statement = ((Statement) token);
      statement.getFactory().parse(this, token);
      return statement;
    }

    // TODO: move this to factory
    ExpressionStatement expr = new ExpressionStatement();
    expr.setExpr(parseExpression());

    return expr;
  }

  /**
   * Parses an expression
   *
   * @return Expression parsed
   */
  public Expression parseExpression() {
    return parseExpression(initialPrecedence);
  }

  /**
   * Parses an expression
   *
   * @return Expression parsed
   */
  public Expression parseExpression(int precedence) {
    Node token = consume();
    Expression prefix = (Expression) token;

    if (prefix == null || (prefix instanceof BinaryOperator && !(prefix instanceof Indecisive))) {
      throw new RuntimeException("Could not parse \"" +
          token.getLiteral() + "\".");
    }

    Expression left = prefix.getFactory().parse(this, null, token, null);

    while (precedence > getPrecedence()) {
      token = consume();

      Expression infix = (Expression) token;

      Factory<?> factory = infix.getFactory();
      if (factory == null) {
        throw new RuntimeException("Could not parse \"" +
            token.getLiteral() + "\".");
      }

      left = infix.getFactory().parse(this, left, token, null);
    }

    return left;
  }

  /**
   * Consumes a node
   *
   * @return The node consumed
   */
  public Node consume() {
    // Make sure we've read the token.
    lookAhead(0);

    return mRead.remove(0);
  }

  /**
   * Consumes a specific node, or throws
   *
   * @param expected The expected Node type
   * @return The node consumed
   */
  public Node consume(Class<?> expected) {
    Node token = lookAhead(0);
    if (token.getClass() != expected) {
      throw new RuntimeException("Expected token " + expected.getCanonicalName() +
          " and found " + token.getClass().getCanonicalName());
    }

    return consume();
  }

  /**
   * Checks whether the next token is of the expected type and consumes if true
   *
   * @param expected Type to test against
   * @return True or False
   */
  public boolean match(Class<?> expected) {
    Node token = lookAhead(0);
    if (token.getClass() != expected) {
      return false;
    }

    consume();
    return true;
  }

  /**
   * Checks whether the next token has the expected literael and consumes if true
   *
   * @param expected Literal to test against
   * @return True or False
   */
  public boolean match(String expected) {
    Node token = lookAhead(0);
    if (!token.getLiteral().equals(expected)) {
      return false;
    }

    consume();
    return true;
  }

  /**
   * Branch to a new symbol table
   *
   * @return the new symbol table
   */
  public SymbolTable branchIn() {
    return branchIn(new SymbolTable());
  }

  /**
   * Branch to a new symbol table
   *
   * @param newtable the new table
   * @return the new symbol table
   */
  public SymbolTable branchIn(SymbolTable newtable) {
    newtable.setParent(symbolTable);
    symbolTable = newtable;
    return symbolTable;
  }

  /**
   * Branch the symbol table back one step
   *
   * @return The old symbol table
   */
  public SymbolTable branchOut() {
    symbolTable = symbolTable.getParent();
    return symbolTable;
  }

  /**
   * Looks ahead in the iterator.
   *
   * @param distance How far to look ahead
   * @return The node that is `distance` nodes from the current
   */
  private Node lookAhead(int distance) {
    // Read in as many as needed.
    while (distance >= mRead.size()) {
      mRead.add(tokens.next());
    }

    // Get the queued token.
    return mRead.get(distance);
  }

  /**
   * Gets the precedence of the next node
   *
   * @return Precedence of the next node
   */
  private int getPrecedence() {
    Node ahead = lookAhead(0);

    if (ahead instanceof BinaryOperator) {
      return ((BinaryOperator) ahead).getPrecedence();
    }

    return initialPrecedence;
  }
}
