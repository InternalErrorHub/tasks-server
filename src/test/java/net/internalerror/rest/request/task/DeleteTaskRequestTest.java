package net.internalerror.rest.request.task;

import net.internalerror.rest.Messages;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;
import net.internalerror.test.SecuredRequestTestBase;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class DeleteTaskRequestTest extends SecuredRequestTestBase<DeleteTaskRequest> {

    @Override
    protected Class<DeleteTaskRequest> getRequestClass() {
        return DeleteTaskRequest.class;
    }

    @Override
    protected String getRoute() {
        return Routes.TASK_DELETE;
    }

    @Override
    protected Map<String, BiConsumer<DeleteTaskRequest, Object>> setters(Map<String, BiConsumer<DeleteTaskRequest, Object>> setters) {
        setters.put("name", (request, value) -> request.setName((String) value));
        return setters;
    }

    @Override
    protected List<RequestTestBase<DeleteTaskRequest>.RequestTest> getRequestTests(List<RequestTestBase<DeleteTaskRequest>.RequestTest> requestTests) {
        requestTests.add(expect(createDefaultRequest(), Messages.NAME_IS_NULL));
        requestTests.add(expect(createRequestWith("name", ""), Messages.NAME_IS_EMPTY));
        return requestTests;
    }

}
