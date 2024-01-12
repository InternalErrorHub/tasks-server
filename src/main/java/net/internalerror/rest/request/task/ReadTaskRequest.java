package net.internalerror.rest.request.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.SecuredRequestBase;

@Getter
@Setter
public class ReadTaskRequest extends SecuredRequestBase {

    @NotNull(message = Messages.NAME_IS_NULL)
    @NotEmpty(message = Messages.NAME_IS_EMPTY)
    private String name;

}
