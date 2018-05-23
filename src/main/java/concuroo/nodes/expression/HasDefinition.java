package concuroo.nodes.expression;

/**
 * This interface is to be used whenever a class needs an definition.
 *
 * @param <T> The type of definition needed.
 */
public interface HasDefinition<T extends Identifier> {

  /**
   * Gets the definition
   *
   * @return The definition
   */
  T getDefinition();

  /**
   * Sets the definition
   *
   * @param definition The definition
   */
  void setDefinition(T definition);
}
