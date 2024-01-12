package net.internalerror.rest.request.user;

import net.internalerror.rest.Messages;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;
import net.internalerror.test.SecuredRequestTestBase;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

class UpdateUserRequestTest extends SecuredRequestTestBase<UpdateUserRequest> {

    @Override
    protected Class<UpdateUserRequest> getRequestClass() {
        return UpdateUserRequest.class;
    }

    @Override
    protected String getRoute() {
        return Routes.USER_UPDATE;
    }

    @Override
    protected Map<String, BiConsumer<UpdateUserRequest, Object>> setters(Map<String, BiConsumer<UpdateUserRequest, Object>> setters) {
        setters.put("firstname", (request, value) -> request.setFirstname((String) value));
        setters.put("lastname", (request, value) -> request.setLastname((String) value));
        return setters;
    }

    @Override
    protected List<RequestTestBase<UpdateUserRequest>.RequestTest> getRequestTests(List<RequestTestBase<UpdateUserRequest>.RequestTest> requestTests) {
        requestTests.add(expect(createDefaultRequest(), Messages.FIRSTNAME_IS_NULL));
        requestTests.add(expect(createDefaultRequest(), Messages.LASTNAME_IS_NULL));
        requestTests.add(expect(createRequestWith("firstname", ""), Messages.FIRSTNAME_IS_EMPTY));
        requestTests.add(expect(createRequestWith("lastname", ""), Messages.LASTNAME_IS_EMPTY));
        return requestTests;
    }

}
