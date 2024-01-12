package net.internalerror.rest.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import net.internalerror.TasksServer;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.RequestBase;

@Getter
@Setter
public class RegisterRequest implements RequestBase {

    @NotNull(message = Messages.FIRSTNAME_IS_NULL)
    @NotEmpty(message = Messages.FIRSTNAME_IS_EMPTY)
    private String firstname;

    @NotNull(message = Messages.LASTNAME_IS_NULL)
    @NotEmpty(message = Messages.LASTNAME_IS_EMPTY)
    private String lastname;

    @NotNull(message = Messages.EMAIL_IS_NULL)
    @NotEmpty(message = Messages.EMAIL_IS_EMPTY)
    @Email(message = Messages.EMAIL_IS_INVALID, regexp = TasksServer.EMAIL_REGEX)
    private String email;

    @NotNull(message = Messages.PASSWORD_IS_NULL)
    @NotEmpty(message = Messages.PASSWORD_IS_EMPTY)
    private String password;

}
