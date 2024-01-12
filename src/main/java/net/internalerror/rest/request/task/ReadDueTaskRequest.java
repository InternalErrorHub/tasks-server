package net.internalerror.rest.request.task;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.SecuredRequestBase;

import java.time.temporal.TemporalUnit;

@Getter
@Setter
public class ReadDueTaskRequest extends SecuredRequestBase {

    @NotNull(message = Messages.DUE_INFO_IS_NULL)
    private DueInfo dueInfo;

    public record DueInfo(TemporalUnit unit, int amount) {

    }

}
