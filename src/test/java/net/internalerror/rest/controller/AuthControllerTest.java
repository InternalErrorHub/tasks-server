package net.internalerror.rest.controller;

import net.internalerror.rest.Messages;
import net.internalerror.rest.exception.ValidationException;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.test.ControllerTestBase;
import org.junit.jupiter.api.RepeatedTest;

class AuthControllerTest extends ControllerTestBase {

  @RepeatedTest(TEST_RUNS)
  void register() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");
    assertDoesNotThrow(() -> authController.register(registerRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void registerPASSWORD_IS_WEAK() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Pass123");
    assertThrows(ValidationException.class, () -> authController.register(registerRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void registerEMAIL_IS_UNAVAILABLE() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");
    assertDoesNotThrow(() -> authController.register(registerRequest));
    assertThrowsValidationException(Messages.EMAIL_IS_UNAVAILABLE, () -> authController.register(registerRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void login() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");
    assertDoesNotThrow(() -> authController.register(registerRequest));

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("max.mustermann@gmail.com");
    loginRequest.setPassword("Passwd@123");
    assertDoesNotThrow(() -> authController.login(loginRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void loginEMAIL_IS_UNREGISTERED() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("max.mustermann@gmail.com");
    loginRequest.setPassword("Passwd@123");
    assertThrowsValidationException(Messages.EMAIL_IS_UNREGISTERED, () -> authController.login(loginRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void loginINVALID_CREDENTIALS() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");
    assertDoesNotThrow(() -> authController.register(registerRequest));

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("max.mustermann@gmail.com");
    loginRequest.setPassword("Passwd@1234");
    assertThrowsValidationException(Messages.INVALID_CREDENTIALS, () -> authController.login(loginRequest));
  }

}