package net.internalerror.rest.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.entity.User;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.rest.response.auth.LoginResponse;
import net.internalerror.rest.response.auth.RegisterResponse;
import net.internalerror.test.ServiceTestBase;
import org.junit.jupiter.api.RepeatedTest;

class AuthServiceTest extends ServiceTestBase {

  @RepeatedTest(TEST_RUNS)
  void register() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");

    RegisterResponse registerResponse = authService.register(registerRequest);
    assertNotNull(registerResponse);
    assertTrue(userRepository.existsByEmail(registerResponse.email()));
  }

  @RepeatedTest(TEST_RUNS)
  void login() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");

    authService.register(registerRequest);

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("max.mustermann@gmail.com");
    loginRequest.setPassword("Passwd@123");

    LoginResponse loginResponse = authService.login(loginRequest);
    assertNotNull(loginResponse);
    assertTrue(securityTokenRepository.existsByToken(loginResponse.token()));
    SecurityToken securityToken = securityTokenRepository.findByToken(loginResponse.token());
    User user = userRepository.findByEmail(loginRequest.getEmail());
    assertEquals(user.getEmail(), securityToken.getUser().getEmail());
  }

  @RepeatedTest(TEST_RUNS)
  void loginWithExistingValidTokenReturnsSameToken() {

    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");

    authService.register(registerRequest);

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("max.mustermann@gmail.com");
    loginRequest.setPassword("Passwd@123");

    LoginResponse loginResponse1 = authService.login(loginRequest);
    LoginResponse loginResponse2 = authService.login(loginRequest);
    assertEquals(loginResponse1.token(), loginResponse2.token());
  }

  @RepeatedTest(TEST_RUNS)
  void loginWithExistingInvalidTokenReturnsNewToken() {

    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setFirstname("Max");
    registerRequest.setLastname("Mustermann");
    registerRequest.setEmail("max.mustermann@gmail.com");
    registerRequest.setPassword("Passwd@123");

    authService.register(registerRequest);

    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("max.mustermann@gmail.com");
    loginRequest.setPassword("Passwd@123");

    LoginResponse loginResponse1 = authService.login(loginRequest);

    SecurityToken securityToken = securityTokenRepository.findByToken(loginResponse1.token());
    securityToken.setValidUntil(Instant.now().minus(10, ChronoUnit.DAYS));
    securityTokenRepository.save(securityToken);

    LoginResponse loginResponse2 = authService.login(loginRequest);
    assertNotEquals(loginResponse1.token(), loginResponse2.token());
  }

}