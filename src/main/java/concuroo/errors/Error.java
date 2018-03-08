package concuroo.errors;

public class Error {

  private final String msg;
  private final ErrorType severity;

  public Error(String msg, ErrorType severity) {
    this.msg = msg;
    this.severity = severity;
  }

  public String getMessage(){
    return msg;
  }

  public ErrorType getError() {
      return severity;
  }
}
