package concuroo.nodes;

import concuroo.CodeGenerator;

/**
 * This interface is to be used with all declarations. Mainly needed for code generation.
 */
public interface Declaration extends Node {

  /**
   * TODO: write docs
   *
   * @param arg TODO: write docs
   */
  void accept(CodeGenerator arg);
}
