package concuroo.language;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.operators.groups.Group;

public class Utilities {

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
