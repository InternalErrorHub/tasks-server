package net.internalerror.test;

import lombok.extern.slf4j.Slf4j;
import net.internalerror.rest.controller.AuthController;
import net.internalerror.rest.controller.TaskController;
import net.internalerror.rest.controller.UserController;
import net.internalerror.rest.exception.ValidationException;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class ControllerTestBase extends CleanDatabaseTestBase {

  @Autowired
  protected AuthController authController;

  @Autowired
  protected UserController userController;

  @Autowired
  protected TaskController taskController;

  protected static void assertThrowsValidationException(String message, Executable executable) {
    ValidationException exception = assertThrows(ValidationException.class, executable);
    log.debug("Expected exception was thrown: {}", exception.getMessage(), exception);
    assertEquals(message, exception.getMessage());
  }

}
