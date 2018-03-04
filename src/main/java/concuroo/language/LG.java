package concuroo.language;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.TokenNode;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Language Grammar
 */
public class LG {

  private final HashMap<String, Object[]> tokens;

  /**
   * Default constructor
   */
  LG() {
    tokens = new HashMap<>();
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
}
