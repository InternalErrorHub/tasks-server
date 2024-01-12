package net.internalerror.rest.request.task;

import net.internalerror.rest.Messages;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;
import net.internalerror.test.SecuredRequestTestBase;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

class UpdateTaskRequestTest extends SecuredRequestTestBase<UpdateTaskRequest> {

    @Override
    protected Class<UpdateTaskRequest> getRequestClass() {
        return UpdateTaskRequest.class;
    }

    @Override
    protected String getRoute() {
        return Routes.TASK_UPDATE;
    }

    @Override
    protected Map<String, BiConsumer<UpdateTaskRequest, Object>> setters(Map<String, BiConsumer<UpdateTaskRequest, Object>> setters) {
        setters.put("name", (request, value) -> request.setName((String) value));
        setters.put("newName", (request, value) -> request.setNewName((String) value));
        setters.put("details", (request, value) -> request.setDetails((String) value));
        setters.put("due", (request, value) -> request.setDue((Instant) value));
        return setters;
    }

    @Override
    protected List<RequestTestBase<UpdateTaskRequest>.RequestTest> getRequestTests(List<RequestTestBase<UpdateTaskRequest>.RequestTest> requestTests) {
        requestTests.add(expect(createDefaultRequest(), Messages.NAME_IS_NULL));
        requestTests.add(expect(createDefaultRequest(), Messages.NEW_NAME_IS_NULL));
        requestTests.add(expect(createDefaultRequest(), Messages.DETAILS_IS_NULL));
        requestTests.add(expect(createDefaultRequest(), Messages.DUE_IS_NULL));
        requestTests.add(expect(createRequestWith("name", ""), Messages.NAME_IS_EMPTY));
        requestTests.add(expect(createRequestWith("newName", ""), Messages.NEW_NAME_IS_EMPTY));
        requestTests.add(expect(createRequestWith("details", ""), Messages.DETAILS_IS_EMPTY));
        requestTests.add(expect(createRequestWith("due", Instant.now().minus(10, ChronoUnit.HOURS)), Messages.DUE_IS_IN_THE_PAST));
        return requestTests;
    }

}
