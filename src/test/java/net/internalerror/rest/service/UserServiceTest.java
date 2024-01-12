package net.internalerror.rest.service;

import net.internalerror.data.entity.User;
import net.internalerror.rest.request.user.UpdateUserRequest;
import net.internalerror.rest.response.user.UpdateUserResponse;
import net.internalerror.test.DataUtil;
import net.internalerror.test.ServiceTestBase;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest extends ServiceTestBase {

    @Autowired
    private DataUtil dataUtil;

    @RepeatedTest(TEST_RUNS)
    void update() {
        DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstname("Max");
        updateUserRequest.setLastname("Mustermann");
        updateUserRequest.setToken(credentials.token());

        UpdateUserResponse updateUserResponse = getUserService().update(updateUserRequest);
        assertNotNull(updateUserResponse);
        assertEquals("Max", updateUserResponse.firstname());
        assertEquals("Mustermann", updateUserResponse.lastname());

        User user = getUserRepository().findByEmail(credentials.email());
        assertEquals("Max", user.getFirstname());
        assertEquals("Mustermann", user.getLastname());
    }

}
