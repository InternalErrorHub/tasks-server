package net.internalerror.rest.request.task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.SecuredRequestBase;

import java.time.Instant;

@ToString
@Getter
@Setter
public class UpdateTaskRequest extends SecuredRequestBase {

    @NotNull(message = Messages.NAME_IS_NULL)
    @NotEmpty(message = Messages.NAME_IS_EMPTY)
    private String name;

    @NotNull(message = Messages.NEW_NAME_IS_NULL)
    @NotEmpty(message = Messages.NEW_NAME_IS_EMPTY)
    private String newName;

    @NotNull(message = Messages.DETAILS_IS_NULL)
    @NotEmpty(message = Messages.DETAILS_IS_EMPTY)
    private String details;

    @NotNull(message = Messages.DUE_IS_NULL)
    @Future(message = Messages.DUE_IS_IN_THE_PAST)
    private Instant due;

}
