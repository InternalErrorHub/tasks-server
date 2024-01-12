package net.internalerror.rest.response.task;

import java.time.Instant;

public record UpdateTaskResponse(String name, String details, Instant due) {

}
