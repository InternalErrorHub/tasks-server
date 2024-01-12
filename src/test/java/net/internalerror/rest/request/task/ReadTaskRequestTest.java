package net.internalerror.rest.request.task;

import net.internalerror.rest.Messages;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;
import net.internalerror.test.SecuredRequestTestBase;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

class ReadTaskRequestTest extends SecuredRequestTestBase<ReadTaskRequest> {

    @Override
    protected Class<ReadTaskRequest> getRequestClass() {
        return ReadTaskRequest.class;
    }

    @Override
    protected String getRoute() {
        return Routes.TASK_READ;
    }

    @Override
    protected Map<String, BiConsumer<ReadTaskRequest, Object>> setters(Map<String, BiConsumer<ReadTaskRequest, Object>> setters) {
        setters.put("name", (request, value) -> request.setName((String) value));
        return setters;
    }

    @Override
    protected List<RequestTestBase<ReadTaskRequest>.RequestTest> getRequestTests(List<RequestTestBase<ReadTaskRequest>.RequestTest> requestTests) {
        requestTests.add(expect(createDefaultRequest(), Messages.NAME_IS_NULL));
        requestTests.add(expect(createRequestWith("name", ""), Messages.NAME_IS_EMPTY));
        return requestTests;
    }

}
