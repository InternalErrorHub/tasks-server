package net.internalerror.rest.service;

import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.entity.User;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.rest.response.auth.LoginResponse;
import net.internalerror.rest.response.auth.RegisterResponse;
import net.internalerror.test.ServiceTestBase;
import org.junit.jupiter.api.RepeatedTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class AuthServiceTest extends ServiceTestBase {

    @RepeatedTest(TEST_RUNS)
    void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Max");
        registerRequest.setLastname("Mustermann");
        registerRequest.setEmail("max.mustermann@gmail.com");
        registerRequest.setPassword("Passwd@123");

        RegisterResponse registerResponse = getAuthService().register(registerRequest);
        assertNotNull(registerResponse);
        assertTrue(getUserRepository().existsByEmail(registerResponse.email()));
    }

    @RepeatedTest(TEST_RUNS)
    void login() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Max");
        registerRequest.setLastname("Mustermann");
        registerRequest.setEmail("max.mustermann@gmail.com");
        registerRequest.setPassword("Passwd@123");

        getAuthService().register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("max.mustermann@gmail.com");
        loginRequest.setPassword("Passwd@123");

        LoginResponse loginResponse = getAuthService().login(loginRequest);
        assertNotNull(loginResponse);
        assertTrue(getSecurityTokenRepository().existsByToken(loginResponse.token()));
        SecurityToken securityToken = getSecurityTokenRepository().findByToken(loginResponse.token());
        User user = getUserRepository().findByEmail(loginRequest.getEmail());
        assertEquals(user.getEmail(), securityToken.getUser().getEmail());
    }

    @RepeatedTest(TEST_RUNS)
    void loginWithExistingValidTokenReturnsSameToken() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Max");
        registerRequest.setLastname("Mustermann");
        registerRequest.setEmail("max.mustermann@gmail.com");
        registerRequest.setPassword("Passwd@123");

        getAuthService().register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("max.mustermann@gmail.com");
        loginRequest.setPassword("Passwd@123");

        LoginResponse loginResponse1 = getAuthService().login(loginRequest);
        LoginResponse loginResponse2 = getAuthService().login(loginRequest);
        assertEquals(loginResponse1.token(), loginResponse2.token());
    }

    @RepeatedTest(TEST_RUNS)
    void loginWithExistingInvalidTokenReturnsNewToken() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("Max");
        registerRequest.setLastname("Mustermann");
        registerRequest.setEmail("max.mustermann@gmail.com");
        registerRequest.setPassword("Passwd@123");

        getAuthService().register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("max.mustermann@gmail.com");
        loginRequest.setPassword("Passwd@123");

        LoginResponse loginResponse1 = getAuthService().login(loginRequest);

        SecurityToken securityToken = getSecurityTokenRepository().findByToken(loginResponse1.token());
        securityToken.setValidUntil(Instant.now().minus(10, ChronoUnit.DAYS));
        getSecurityTokenRepository().save(securityToken);

        LoginResponse loginResponse2 = getAuthService().login(loginRequest);
        assertNotEquals(loginResponse1.token(), loginResponse2.token());
    }

}