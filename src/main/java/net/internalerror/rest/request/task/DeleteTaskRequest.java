package net.internalerror.rest.request.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.SecuredRequestBase;

@ToString
@Getter
@Setter
public class DeleteTaskRequest extends SecuredRequestBase {

    @NotNull(message = Messages.NAME_IS_NULL)
    @NotEmpty(message = Messages.NAME_IS_EMPTY)
    private String name;

}
