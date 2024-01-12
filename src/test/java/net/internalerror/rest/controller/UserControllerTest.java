package net.internalerror.rest.controller;

import net.internalerror.data.entity.SecurityToken;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.user.UpdateUserRequest;
import net.internalerror.test.ControllerTestBase;
import net.internalerror.test.DataUtil;
import org.junit.jupiter.api.RepeatedTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class UserControllerTest extends ControllerTestBase {

    @RepeatedTest(TEST_RUNS)
    void update() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstname("Max");
        updateUserRequest.setLastname("Mustermann");
        updateUserRequest.setToken(testCredentials.token());
        assertDoesNotThrow(() -> getUserController().update(updateUserRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void updateUNAUTHORIZED_REQUEST1() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstname("Max");
        updateUserRequest.setLastname("Mustermann");
        updateUserRequest.setToken("");
        assertThrowsValidationException(Messages.UNAUTHORIZED_REQUEST, () -> getUserController().update(updateUserRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void updateUNAUTHORIZED_REQUEST2() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstname("Max");
        updateUserRequest.setLastname("Mustermann");
        updateUserRequest.setToken("ABC");
        assertThrowsValidationException(Messages.UNAUTHORIZED_REQUEST, () -> getUserController().update(updateUserRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void updateUNAUTHORIZED_REQUEST3() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        SecurityToken securityToken = getSecurityTokenRepository().findByToken(testCredentials.token());
        securityToken.setValidUntil(Instant.now().minus(10, ChronoUnit.DAYS));
        getSecurityTokenRepository().save(securityToken);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstname("Max");
        updateUserRequest.setLastname("Mustermann");
        updateUserRequest.setToken(securityToken.getToken());
        assertThrowsValidationException(Messages.UNAUTHORIZED_REQUEST, () -> getUserController().update(updateUserRequest));
    }

}