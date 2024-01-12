package net.internalerror.rest.response.task;

import java.util.List;

public record ReadDueTaskResponse(List<ReadAllTaskResponse.TaskInfo> list) {

}
