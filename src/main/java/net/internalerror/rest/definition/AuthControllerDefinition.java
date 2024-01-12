package net.internalerror.rest.definition;

import jakarta.validation.Valid;
import net.internalerror.rest.Routes;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.rest.response.auth.LoginResponse;
import net.internalerror.rest.response.auth.RegisterResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDefinition {

    @PostMapping(name = "Register", value = Routes.AUTH_REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RegisterResponse register(@RequestBody @Valid RegisterRequest request);

    @PostMapping(name = "Login", value = Routes.AUTH_LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LoginResponse login(@RequestBody @Valid LoginRequest request);

}
