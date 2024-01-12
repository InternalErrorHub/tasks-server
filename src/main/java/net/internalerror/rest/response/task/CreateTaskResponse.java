package net.internalerror.rest.response.task;

import java.time.Instant;

public record CreateTaskResponse(String name, Instant due) {

}
