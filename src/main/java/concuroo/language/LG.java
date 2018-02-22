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
  // TODO: clean this algorithm
  public Pair<Node, Integer> lookupStatement(Node[] tokens, SymbolTable symtable) {
    for (Pair<Object[][], StatementFactory<?>> pair : statements) {
      Object[][] perms = pair.getLeft();
      for (Object[] perm : perms) {
        boolean hit = true;
        int j = 0;
        List<Node> arguments = new ArrayList<>();
        for (int i = 0; i < perm.length && j < tokens.length; i++) {
          // It's a string, which means it's a token
          if (perm[i] instanceof String) {
            Object[] p = this.tokens.get(perm[i].toString());
            if ((p.length == 1 && ((Factory<?>) p[0]).is(tokens[j])) ||
                (p.length == 2 && (tokens[j] instanceof TokenNode) &&
                    ((TokenNode) tokens[j]).getKey().equals(perm[i].toString()))) {
              j++;
              continue;
            }
            hit = false;
            break;
          } else if (perm[i] instanceof Class && perm[i] == Expression.class) {
            // The stuff need to be expressions
            if (!(tokens[j] instanceof Expression)) {
              hit = false;
              break;
            }

            int from = j;

            // If the last thing we saw was a '(' then we expect a ')'
            if (i > 0 && perm[i - 1] instanceof String && perm[i - 1].equals("(")) {
              j = Utilities.findClosingToken(tokens, j, Parenthesis.class);
            } else {
              // If not, we have no other choice than to do this.
              while (j < tokens.length && tokens[j] instanceof Expression) {
                j++;
              }
            }

            Node[] t = Arrays.copyOfRange(tokens, from, j);
            if (j < tokens.length && tokens[j].getLiteral().equals(";")) {
              j++;
            }
            arguments.add(Parser.ExpressionAST(t, new SymbolTable()));
          } else if (perm[i] instanceof Class && perm[i] == Statement.class) {
            // Recursively look for statement
            Pair<Node, Integer> st = lookupStatement(
                Arrays.copyOfRange(tokens, j, tokens.length), symtable);
            if (st == null) {
              hit = false;
              break;
            }
            j += st.getValue();
            arguments.add(st.getKey());
          } else if (perm[i] instanceof Class<?>[]) {
            Class<?> c = ((Class<?>[]) perm[i])[0];
            if (c == Statement.class) {
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
            } else {
              hit = false;
              break;
            }
          } else {
            hit = false;
            break;
          }
        }
        if (hit) {
          Node n = pair.getValue().makeInstance(tokens, arguments, symtable);
          return new ImmutablePair<>(n, j);
        }
      }
    }
    return null;
  }
}
