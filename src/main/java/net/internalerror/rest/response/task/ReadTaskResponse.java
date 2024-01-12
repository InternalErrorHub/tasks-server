package net.internalerror.rest.response.task;

import java.time.Instant;

public record ReadTaskResponse(String name, String details, Instant due) {

}
