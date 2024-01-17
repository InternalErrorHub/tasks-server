package net.internalerror.rest.response.task;

import java.time.Instant;
import java.util.List;

public record ReadAllTaskResponse(List<TaskInfo> list) {

  public record TaskInfo(String name, Instant due) {

  }

}
