package net.internalerror.rest.request.auth;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import net.internalerror.rest.Messages;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;

class RegisterRequestTest extends RequestTestBase<RegisterRequest> {

  @Override
  protected Class<RegisterRequest> getRequestClass() {
    return RegisterRequest.class;
  }

  @Override
  protected String getRoute() {
    return Routes.AUTH_REGISTER;
  }

  @Override
  protected Map<String, BiConsumer<RegisterRequest, Object>> setters(Map<String, BiConsumer<RegisterRequest, Object>> setters) {
    setters.put("firstname", (request, value) -> request.setFirstname((String) value));
    setters.put("lastname", (request, value) -> request.setLastname((String) value));
    setters.put("email", (request, value) -> request.setEmail((String) value));
    setters.put("password", (request, value) -> request.setPassword((String) value));
    return setters;
  }

  @Override
  protected List<RequestTestBase<RegisterRequest>.RequestTest> getRequestTests(List<RequestTestBase<RegisterRequest>.RequestTest> requestTests) {
    requestTests.add(expect(createDefaultRequest(), Messages.FIRSTNAME_IS_NULL));
    requestTests.add(expect(createDefaultRequest(), Messages.LASTNAME_IS_NULL));
    requestTests.add(expect(createDefaultRequest(), Messages.EMAIL_IS_NULL));
    requestTests.add(expect(createDefaultRequest(), Messages.PASSWORD_IS_NULL));
    requestTests.add(expect(createRequestWith("firstname", ""), Messages.FIRSTNAME_IS_EMPTY));
    requestTests.add(expect(createRequestWith("lastname", ""), Messages.LASTNAME_IS_EMPTY));
    requestTests.add(expect(createRequestWith("email", ""), Messages.EMAIL_IS_EMPTY));
    requestTests.add(expect(createRequestWith("password", ""), Messages.PASSWORD_IS_EMPTY));
    requestTests.add(expect(createRequestWith("email", "max.mustermann"), Messages.EMAIL_IS_INVALID));
    return requestTests;
  }

}