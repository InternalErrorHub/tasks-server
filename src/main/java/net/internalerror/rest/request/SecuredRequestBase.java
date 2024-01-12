package net.internalerror.rest.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SecuredRequestBase extends RequestBase {

    @NotNull(message = "TOKEN_IS_NULL")
    @NotEmpty(message = "TOKEN_IS_EMPTY")
    private String token;

}
