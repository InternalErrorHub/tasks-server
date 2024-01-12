package net.internalerror.rest.request.auth;

import net.internalerror.rest.Messages;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class LoginRequestTest extends RequestTestBase<LoginRequest> {

    @Override
    protected Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }

    @Override
    protected String getRoute() {
        return Routes.AUTH_LOGIN;
    }

    @Override
    protected Map<String, BiConsumer<LoginRequest, Object>> setters(Map<String, BiConsumer<LoginRequest, Object>> setters) {
        setters.put("email", (request, value) -> request.setEmail((String) value));
        setters.put("password", (request, value) -> request.setPassword((String) value));
        return setters;
    }

    @Override
    protected List<RequestTestBase<LoginRequest>.RequestTest> getRequestTests(List<RequestTestBase<LoginRequest>.RequestTest> requestTests) {
        requestTests.add(expect(createDefaultRequest(), Messages.EMAIL_IS_NULL));
        requestTests.add(expect(createDefaultRequest(), Messages.PASSWORD_IS_NULL));
        requestTests.add(expect(createRequestWith("email", ""), Messages.EMAIL_IS_EMPTY));
        requestTests.add(expect(createRequestWith("password", ""), Messages.PASSWORD_IS_EMPTY));
        return requestTests;
    }

}
