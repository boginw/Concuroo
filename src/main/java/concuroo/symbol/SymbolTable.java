package concuroo.symbol;

import concuroo.nodes.Node;
import concuroo.nodes.expression.unaryExpression.Identifier;
import java.util.Hashtable;
import java.util.Map;

public class SymbolTable {

  private Hashtable<String, Node> symbols;
  private SymbolTable parent;

  /**
   * Default constructor
   */
  public SymbolTable() {
    this(null);
  }

  /**
   * Overload constructor for adding parent directly
   *
   * @param parent Parent symbol table
   */
  public SymbolTable(SymbolTable parent) {
    this.parent = parent;
    symbols = new Hashtable<>();
  }


  /**
   * Create a shallow copy of this scope. Used in case functions are are recursively called. If we
   * wouldn't create a copy in such cases, changing the variables would result in changes ro the
   * Maps from other "recursive scopes".
   *
   * @return A copy of the SymbolTable
   */
  public SymbolTable copy() {
    SymbolTable s = new SymbolTable();
    s.setParent(this.parent);
    s.symbols = new Hashtable<>(this.symbols);
    return s;
  }

  /**
   * Checks if this SymbolTable is the root and thereby the global scope
   *
   * @return Whether or not this SymbolTable is the global scope
   */
  public boolean isGlobalScope() {
    return parent == null;
  }

  /**
   * Finds a given symbol by searching the SymbolTable's hashtable, if it cannot be found, we search
   * the parent SymbolTable's hashtable.
   *
   * @param literal Name of the symbol to search for
   * @return The node of the given symbol.
   */
  public Node lookup(String literal) {
    Node n = symbols.get(literal);
    if (n != null) {
      return symbols.get(literal);
    }
    if (parent != null) {
      return parent.lookup(literal);
    }
    // TODO: throw
    throw new RuntimeException("Identifier not found");
  }

  /**
   * Inserts a symbol to the table
   *
   * @param s Symbol to append
   */
  public void insert(Identifier s) {
    symbols.put(s.getIdentifier().getLiteral(), s);
  }


  /**
   * Sets the SymbolTable's parent
   *
   * @param parent Parent to setparameterList
   */
  public void setParent(SymbolTable parent) {
    this.parent = parent;
  }


  /**
   * Gets the current parent of the SymbolTable
   *
   * @return The parent of the SymbolTable
   */
  public SymbolTable getParent() {
    return parent;
  }

  /**
   * Serializes the SymbolTable without it's parent
   *
   * @return Serialized SymbolTable
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Node> var : symbols.entrySet()) {
      sb.append(var.getKey()).append("->").append(var.getValue()).append("\n");
    }
    return sb.toString();
  }
}
