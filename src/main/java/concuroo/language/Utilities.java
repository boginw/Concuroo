package concuroo.language;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.operators.groups.Group;

public class Utilities {

  /**
   * Given a list of tokens this method can find a closing bracket
   *
   * @param tokens List of tokens/nodes to look in
   * @param start Where to start looking
   * @param type The type of the bracket to look for
   * @param <T> Bracket type
   * @return The index of the closing bracket or -1 for not found
   */
  public static <T extends Group> int findClosingToken(Node[] tokens, int start, Class<T> type) {
    int required = 1, i = start;
    for (; i < tokens.length && required > 0; i++) {
      if (type.isInstance(tokens[i])) {
        required += type.cast(tokens[i]).isStart() ? 1 : -1;
      }
    }

    return required == 0 ? i - 1 : -1;
  }
}
