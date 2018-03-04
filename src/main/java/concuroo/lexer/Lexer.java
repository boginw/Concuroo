package concuroo.lexer;

import concuroo.language.LG;
import concuroo.nodes.EOF;
import concuroo.nodes.Node;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lexer implements Iterator<Node> {

  private final SymbolTable symtable;
  private final LG lg;
  private String input;
  private int pos; // points to current char
  private int readPos; // after current char
  private char ch; // current char under examination

  /**
   * Default constructor
   *
   * @param symtable Symboltable of the current scope
   * @param lg Language grammar
   */
  public Lexer(SymbolTable symtable, LG lg) {
    this.symtable = symtable;
    this.lg = lg;
  }

  /**
   * Resets the lexer to a new input
   *
   * @param input String to be lexed
   */
  public void reset(String input) {
    this.input = input;
    pos = 0;
    readPos = 0;
    ch = 0;
    readChar();
  }

  /**
   * Performs a lexical analysis on the input
   *
   * @param input Input to lex
   * @return A list of tokens in Node form
   */
  public Node[] lex(String input) {
    this.input = input;
    pos = 0;
    readPos = 0;
    ch = 0;
    readChar();

    List<Node> symbols = new ArrayList<>();
    for (Node t = nextNode(); !(t instanceof EOF); t = nextNode()) {
      symbols.add(t);
    }

    return symbols.toArray(new Node[symbols.size()]);
  }

  /**
   * Reads a character
   */
  private void readChar() {
    if (readPos >= input.length()) {
      ch = 0;
    } else {
      ch = input.charAt(readPos);
    }
    pos = readPos;
    readPos++;
  }

  /**
   * Finds the next token
   *
   * @return The next token
   */
  private Node nextNode() {
    skipWhitespace();

    if (pos == input.length()) {
      return new EOF();
    }

    Node t = lg.lookupToken(input.substring(pos));

    readPos = pos + t.getLiteral().length();
    readChar();

    return t;
  }

  /**
   * Skips until the next character is not a whitespace character
   */
  private void skipWhitespace() {
    while (Character.isWhitespace(ch)) {
      readChar();
    }
  }

  @Override
  public boolean hasNext() {
    return readPos != input.length();
  }

  @Override
  public Node next() {
    return nextNode();
  }
}
