package concuroo.language;

import concuroo.factories.Factory;
import concuroo.factories.statement.StatementFactory;
import concuroo.nodes.Node;
import concuroo.nodes.TokenNode;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.statements.Statement;
import concuroo.parser.Parser;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Language Grammar
 */
public class LG {

  private final HashMap<String, Object[]> tokens;
  private final List<Pair<Object[][], StatementFactory<?>>> statements;

  /**
   * Default constructor
   */
  LG() {
    tokens = new HashMap<>();
    statements = new ArrayList<>();
  }

  /**
   * Registers a token to the grammar
   *
   * @param key Key of the token
   * @param factory The factory that can recognize and create it
   */
  public void registerToken(String key, Factory<?> factory) {
    tokens.put(key, new Object[]{factory});
  }

  /**
   * Registers a token to the grammar
   *
   * @param key Key of the token
   * @param pattern RegEx pattern
   * @param type Type of the token
   */
  public void registerToken(String key, String pattern, TokenType type) {
    tokens.put(key, new Object[]{pattern, type});
  }

  /**
   * Registers a statement to the grammar
   *
   * @param factory The factory that can create it
   * @param objects The patterns of the statement
   */
  public void registerStatement(Factory<?> factory, Object[][] objects) {
    statements.add(new ImmutablePair<>(objects, (StatementFactory<?>) factory));
  }

  /**
   * Looks at all the tokens registered and finds the one that recognizes the input
   *
   * @param input Literal string to look for
   * @return A token that recognizes the input
   */
  public Node lookupToken(String input) {
    String found;
    for (Map.Entry<String, Object[]> t : tokens.entrySet()) {
      Object[] def = t.getValue();
      if (def.length == 2) {
        String pattern = (String) def[0];
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.find()) {
          found = m.group();
          return new TokenNode(t.getKey(), (TokenType) def[1], found);
        }
      } else if (def.length == 1) {
        Factory<?> f = (Factory<?>) def[0];
        int newPos = f.is(input);
        if (newPos != -1) {
          found = input.substring(0, newPos);
          return f.makeNode(found);
        }
      }
    }

    // TODO: throw
    return null;
  }

  /**
   * Looks at all the statement patterns and finds the one that recognizes the tokens
   *
   * @param tokens Tokens to look at
   * @param symtable The SymbolTable of the current scope
   * @return A Pair with the Statement and how many tokens it is long
   */
  public Pair<Node, Integer> lookupStatement(Node[] tokens, SymbolTable symtable) {
    for (Pair<Object[][], StatementFactory<?>> pair : statements) {
      Object[][] perms = pair.getLeft();
      for (Object[] perm : perms) {
        Pair<List<Node>, Integer> arguments = getArguments(perm, tokens, symtable);
        if (arguments.getLeft() != null) {
          Node n = pair.getValue().makeInstance(tokens, arguments.getLeft(), symtable);
          return new ImmutablePair<>(n, arguments.getRight());
        }
      }
    }
    return null;
  }

  /**
   * Gets all arguments for a permutation.
   *
   * @param perm The permutation object array.
   * @param tokens Tokens to look at.
   * @param symtable The SymbolTable of the current scope.
   * @return A Pair with the arguments and the token count pointer.
   */
  private Pair<List<Node>, Integer> getArguments(Object[] perm, Node[] tokens,
      SymbolTable symtable) {
    boolean hit = true;
    int j = 0;
    List<Node> arguments = new ArrayList<>();
    for (int i = 0; i < perm.length && j < tokens.length; i++) {
      if (this.isToken(perm[i])) {
        if (this.tokenExists(perm[i], tokens[j])) {
          j++;
          continue;
        }
        hit = false;
        break;
      } else if (this.isExpression(perm[i])) {
        if (!(tokens[j] instanceof Expression)) {
          hit = false;
          break;
        }
        int from = j;
        j = this.findExpressionEnd(perm, i, from, tokens);
        Node[] t = Arrays.copyOfRange(tokens, from, j);
        if (j < tokens.length && tokens[j].getLiteral().equals(";")) {
          j++;
        }
        arguments.add(Parser.ExpressionAST(t, new SymbolTable()));
      } else if (this.isStatement(perm[i])) {
        Pair<Node, Integer> st = lookupStatement(
            Arrays.copyOfRange(tokens, j, tokens.length), symtable);
        if (st == null) {
          hit = false;
          break;
        }
        j += st.getValue();
        arguments.add(st.getKey());

      } else if (this.isClass(perm[i])) {
        Class<?> c = ((Class<?>[]) perm[i])[0];
        if (c == Statement.class) {
          j = findBlockStatement(tokens, symtable, j, arguments);
        } else {
          hit = false;
          break;
        }
      } else {
        hit = false;
        break;
      }
    }
    if (!hit) {
      arguments = null;
    }
    return new ImmutablePair<>(arguments, j);
  }

  /**
   * Finds and evaluates a block statement. It directly adds the "result" from the recursive call on
   * the current arguments-list.
   *
   * @param tokens The tokens to look at.
   * @param symtable The SymbolTable for the current scope.
   * @param j The Token count pointer.
   * @param arguments The argument list.
   * @return The new token count pointer.
   */
  // TODO: CONSIDER REWRITE THE Arrays.copyOfRange PART
  private int findBlockStatement(Node[] tokens, SymbolTable symtable, int j, List<Node> arguments) {
    int end = tokens.length;
    if (j > 0 && tokens.length > 1 && tokens[j - 1].getLiteral().equals("{")) {
      end = Utilities.findClosingToken(tokens, j, Curly.class);
    }

    while (j < end) {
      Pair<Node, Integer> st = lookupStatement(
          Arrays.copyOfRange(tokens, j, tokens.length), symtable);

      if (st == null) {
        break;
      }
      arguments.add(st.getKey());
      j += st.getValue();
    }
    return j;
  }

  /**
   * Finds the end of an expression.
   *
   * @param perm The permutation array
   * @param i The permutation array pointer
   * @param from The token count pointer to start at.
   * @param tokens The tokens to look at.
   * @return A pointer for the end of the expression.
   */
  private int findExpressionEnd(Object[] perm, int i, int from, Node[] tokens) {
    int end = from;

    // If the last thing we saw was a '(' then we expect a ')'
    if (i > 0 && isOpeningParenthesis(perm[i - 1])) {
      end = Utilities.findClosingToken(tokens, end, Parenthesis.class);
    } else {
      // If not, we have no other choice than to do this.
      while (end < tokens.length && tokens[end] instanceof Expression) {
        end++;
      }
    }

    return end;
  }

  /**
   * Determine whether or not a permutation is a class.
   *
   * @param perm the permutation
   * @return True or False.
   */
  private boolean isClass(Object perm) {
    return perm instanceof Class<?>[];
  }

  /**
   * Determine whether or not a permutation is a Statement.
   *
   * @param perm the permutation
   * @return True or False.
   */
  private boolean isStatement(Object perm) {
    return perm instanceof Class && perm == Statement.class;
  }

  /**
   * Determine whether or not a permutation is a open parenthesis.
   *
   * @param perm the permutation
   * @return True or False.
   */
  private boolean isOpeningParenthesis(Object perm) {
    return perm instanceof String && perm.equals("(");
  }

  /**
   * Determine whether or not a permutation is a Expression.
   *
   * @param perm the permutation
   * @return True or False.
   */
  private boolean isExpression(Object perm) {
    return perm instanceof Class && perm == Expression.class;
  }

  /**
   * Determine whether or not a permutation is a Token.
   *
   * @param perm the permutation
   * @return True or False.
   */
  private boolean isToken(Object perm) {
    return perm instanceof String;
  }

  /**
   * Determine whether or not a token exists.
   *
   * @param perm the permutation.
   * @param token the token to look at.
   * @return True or False.
   */
  private boolean tokenExists(Object perm, Node token) {
    Object[] p = this.tokens.get(perm.toString());
    return (p.length == 1 && ((Factory<?>) p[0]).is(token)) ||
        (p.length == 2 && (token instanceof TokenNode) &&
            ((TokenNode) token).getKey().equals(perm.toString()));
  }
}
