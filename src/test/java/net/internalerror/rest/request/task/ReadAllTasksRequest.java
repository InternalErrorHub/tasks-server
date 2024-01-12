package net.internalerror.rest.request.task;

import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;
import net.internalerror.test.SecuredRequestTestBase;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ReadAllTasksRequest extends SecuredRequestTestBase<ReadAllTaskRequest> {

    @Override
    protected Class<ReadAllTaskRequest> getRequestClass() {
        return ReadAllTaskRequest.class;
    }

    @Override
    protected String getRoute() {
        return Routes.TASK_READ_ALL;
    }

    @Override
    protected Map<String, BiConsumer<ReadAllTaskRequest, Object>> setters(Map<String, BiConsumer<ReadAllTaskRequest, Object>> setters) {
        return setters;
    }

    @Override
    protected List<RequestTestBase<ReadAllTaskRequest>.RequestTest> getRequestTests(List<RequestTestBase<ReadAllTaskRequest>.RequestTest> requestTests) {
        return requestTests;
    }

}
