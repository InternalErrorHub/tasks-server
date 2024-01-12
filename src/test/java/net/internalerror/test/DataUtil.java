package net.internalerror.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.rest.controller.AuthController;
import net.internalerror.rest.controller.TaskController;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.rest.request.task.CreateTaskRequest;
import net.internalerror.rest.response.auth.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataUtil {

    @Autowired
    private AuthController authController;

    @Autowired
    private TaskController taskController;

    public TestCredentials createTestCredentials() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname(UUID.randomUUID().toString());
        registerRequest.setLastname(UUID.randomUUID().toString());
        registerRequest.setEmail(UUID.randomUUID() + "@gmail.com");
        registerRequest.setPassword("Passwd@123");

        assertDoesNotThrow(() -> authController.register(registerRequest));
        log.debug("Registered test user: ");
        log.debug("\tFirstname: {}", registerRequest.getFirstname());
        log.debug("\tLastname: {}", registerRequest.getLastname());
        log.debug("\tEmail: {}", registerRequest.getEmail());
        log.debug("\tPassword: {}", registerRequest.getPassword());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(registerRequest.getEmail());
        loginRequest.setPassword(registerRequest.getPassword());

        LoginResponse loginResponse = assertDoesNotThrow(() -> authController.login(loginRequest));
        log.debug("Logged in test user: ");
        log.debug("\tEmail: {}", loginRequest.getEmail());
        log.debug("\tToken: {}", loginResponse.token());

        return new TestCredentials(registerRequest.getEmail(), loginResponse.token());
    }

    public TestTask createTestTask(TestCredentials credentials) {
        return createTestTask(Instant.now(), credentials);
    }

    public TestTask createTestTask(Instant due, TestCredentials credentials) {

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setName(UUID.randomUUID().toString());
        createTaskRequest.setDetails(UUID.randomUUID().toString());
        createTaskRequest.setDue(due);
        createTaskRequest.setToken(credentials.token());

        log.debug("Created test task: ");
        log.debug("\tName: {}", createTaskRequest.getName());
        log.debug("\tDetails: {}", createTaskRequest.getDetails());
        log.debug("\tDue: {}", createTaskRequest.getDue());

        taskController.create(createTaskRequest);

        return new TestTask(createTaskRequest.getName(), createTaskRequest.getDetails(), createTaskRequest.getDue());
    }

    public record TestTask(String name, String details, Instant due) {

    }

    public record TestCredentials(String email, String token) {

    }

}
