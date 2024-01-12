package net.internalerror.test;

import net.internalerror.rest.Messages;
import net.internalerror.rest.request.SecuredRequestBase;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class SecuredRequestTestBase<R extends SecuredRequestBase> extends RequestTestBase<R> {

    @Override
    protected Map<String, BiConsumer<R, Object>> setters() {
        Map<String, BiConsumer<R, Object>> setters = super.setters();
        setters.put("token", (request, value) -> request.setToken((String) value));
        return setters;
    }

    @Override
    protected List<RequestTestBase<R>.RequestTest> getRequestTests() {
        List<RequestTestBase<R>.RequestTest> requestTests = super.getRequestTests();
        requestTests.add(expect(createDefaultRequest(), Messages.TOKEN_IS_NULL));
        requestTests.add(expect(createRequestWith("token", ""), Messages.TOKEN_IS_EMPTY));
        return requestTests;
    }

}
