package net.internalerror.rest.request.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.RequestBase;

@ToString
@Getter
@Setter
public class LoginRequest extends RequestBase {

    @NotNull(message = Messages.EMAIL_IS_NULL)
    @NotEmpty(message = Messages.EMAIL_IS_EMPTY)
    private String email;

    @ToString.Exclude
    @NotNull(message = Messages.PASSWORD_IS_NULL)
    @NotEmpty(message = Messages.PASSWORD_IS_EMPTY)
    private String password;

}
