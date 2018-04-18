package concuroo.errors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ErrorContainerTest {

  private ErrorCollection errors;

  @Before
  public void BeforeEachTest() {
    errors = new ErrorCollection();
  }

  @Test
  public void reportOneError() {
    String expectedString = "Invalid Operator";
    errors.reportError(new Error(expectedString, ErrorType.error));
    Error expected = new Error(expectedString, ErrorType.error);

    assertEquals(expectedString, errors.getAllErrors().get(0).getMessage());
    assertEquals(errors.getErrorCount(), 1);
    assertTrue(errors.isAnyErrorsOfType(ErrorType.error));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.warning));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.notice));
    assertEquals(errors.getErrorsOfType(ErrorType.error).get(0).getError(), expected.getError());
  }

  @Test
  public void reportFiveErrorOfSameType() {

    String expectedString = "Invalid Operator";
    int size = 5;
    for (int i = 1; i <= size; i++) {
      errors.reportError(new Error(expectedString + i, ErrorType.error));
    }
    Error expected = new Error(expectedString + size, ErrorType.error);

    assertEquals(errors.getErrorCount(), size);
    assertTrue(errors.isAnyErrorsOfType(ErrorType.error));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.warning));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.notice));
    assertEquals(errors.getErrorsOfType(ErrorType.error).get(size - 1).getError(),
        expected.getError());
  }

  @Test
  public void reportOneOfEachErrorType() {
    String expectedString = "Invalid Operator";
    errors.reportError(new Error(expectedString, ErrorType.error));
    errors.reportError(new Error(expectedString, ErrorType.warning));
    errors.reportError(new Error(expectedString, ErrorType.notice));
    Error expected = new Error(expectedString, ErrorType.warning);

    assertEquals(errors.getErrorCount(), 3);
    assertTrue(errors.isAnyErrorsOfType(ErrorType.error));
    assertTrue(errors.isAnyErrorsOfType(ErrorType.warning));
    assertTrue(errors.isAnyErrorsOfType(ErrorType.notice));
    assertEquals(errors.getErrorsOfType(ErrorType.warning).get(0).getError(), expected.getError());
    assertEquals(errors.getAllErrors().get(1).getError(), expected.getError());
  }

  @Test
  public void noErrorsReported() {
    assertEquals(errors.getErrorCount(), 0);
    assertEquals(errors.getAllErrors().size(), 0);
    assertNull(errors.getErrorsOfType(ErrorType.error));
    assertNull(errors.getErrorsOfType(ErrorType.warning));
    assertNull(errors.getErrorsOfType(ErrorType.notice));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.error));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.warning));
    assertFalse(errors.isAnyErrorsOfType(ErrorType.notice));
  }
}