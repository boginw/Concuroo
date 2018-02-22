package concuroo.symbol;

import concuroo.nodes.Node;
import java.util.Hashtable;

public class SymbolTable {

  private SymbolTable parent;
  private final Hashtable<String, Node> symbols;

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
    return null;
  }

  /**
   * Inserts a symbol to the table
   *
   * @param s Symbol to append
   */
  public void insert(Node s) {
    symbols.put(s.getLiteral(), s);
  }

  /**
   * Sets the SymbolTable's parent
   *
   * @param parent Parent to set
   */
  public void setParent(SymbolTable parent) {
    this.parent = parent;
  }
}
