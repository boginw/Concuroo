package concuroo;

public class Builder {

  private StringBuilder stream = new StringBuilder();
  private StringBuilder head = new StringBuilder();
  private StringBuilder precompiler = new StringBuilder();
  private StringBuilder structs = new StringBuilder();
  private StringBuilder globals = new StringBuilder();
  private boolean prototyping = false;
  private boolean goParams = false;
  private boolean globalDeclaration = false;

  /**
   * Add string to builder
   *
   * @param string String to add
   */
  public void add(String string) {

    if (globalDeclaration) {
      globals.append(string);
      return;
    }

    if (goParams) {
      structs.append(string);
      return;
    }

    if (prototyping) {
      head.append(string);
    }

    stream.append(string);
  }

  /**
   * Deletes the last character from builder
   */
  public void removeLastCharacter() {
    stream.deleteCharAt(stream.length() - 1);
  }

  /**
   * Gets the final output from the builder
   *
   * @return The output from the builder
   */
  public String getOutput() {
    return precompiler.toString() + structs.toString() + globals.toString() + head.toString()
        + stream.toString();
  }

  public void startPrototype() {
    this.prototyping = true;
  }

  public void startGoParamsStruct() {
    this.goParams = true;
  }

  public void endPrototype() {
    this.prototyping = false;
    head.append(";\n");
  }

  public void endGoParamsStruct() {
    this.goParams = false;
  }

  public boolean isGoParams() {
    return goParams;
  }

  public void clear() {
    this.stream = new StringBuilder();
    this.head = new StringBuilder();
    this.precompiler = new StringBuilder();
    this.structs = new StringBuilder();
  }

  public void addToPreCompiler(String string) {
    this.precompiler.append(string);
  }

  public void startGlobalVariable() {
    this.globalDeclaration = true;
  }

  public void stopGlobalVariable() {
    this.globalDeclaration = false;
  }

}
