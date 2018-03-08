package concuroo.errors;

import java.util.ArrayList;
import java.util.List;

public class ErrorCollection {

  private ArrayList<Error> errors = new ArrayList<>();

  public void reportError(Error error) {
    this.errors.add(error);
  }

  public List<Error> getAllErrors() {
    return errors;
  }

  public List<Error> getErrorsOfType(ErrorType errorType) {
    List<Error> errorsOfType = new ArrayList<>();
    for (Error err : errors) {
      if (err.getError() == errorType) {
        errorsOfType.add(err);
      }
    }
    if (errorsOfType.size() != 0) {
      return errorsOfType;
    }

    return null;
  }

  public int getErrorCount() {
    return errors.size();
  }

  public boolean isAnyErrorsOfType(ErrorType errorType) {
    for (Error err : errors) {
      if (err.getError() == errorType) {
        return true;
      }
    }

    return false;
  }
}
