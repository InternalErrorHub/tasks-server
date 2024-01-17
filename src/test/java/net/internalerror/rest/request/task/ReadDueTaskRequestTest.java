package net.internalerror.rest.request.task;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import net.internalerror.rest.Routes;
import net.internalerror.test.RequestTestBase;
import net.internalerror.test.SecuredRequestTestBase;

class ReadDueTaskRequestTest extends SecuredRequestTestBase<ReadDueTaskRequest> {

  @Override
  protected Class<ReadDueTaskRequest> getRequestClass() {
    return ReadDueTaskRequest.class;
  }

  @Override
  protected String getRoute() {
    return Routes.TASK_READ_ALL;
  }

  @Override
  protected Map<String, BiConsumer<ReadDueTaskRequest, Object>> setters(Map<String, BiConsumer<ReadDueTaskRequest, Object>> setters) {
    return setters;
  }

  @Override
  protected List<RequestTestBase<ReadDueTaskRequest>.RequestTest> getRequestTests(List<RequestTestBase<ReadDueTaskRequest>.RequestTest> requestTests) {
    return requestTests;
  }

}
