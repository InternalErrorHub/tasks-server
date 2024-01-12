package net.internalerror.rest.controller;

import net.internalerror.rest.request.user.UpdateUserRequest;
import net.internalerror.test.ControllerTestBase;
import net.internalerror.test.DataUtil;
import org.junit.jupiter.api.RepeatedTest;

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

}