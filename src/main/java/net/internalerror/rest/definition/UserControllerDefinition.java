package net.internalerror.rest.definition;

import jakarta.validation.Valid;
import net.internalerror.rest.Routes;
import net.internalerror.rest.request.user.UpdateUserRequest;
import net.internalerror.rest.response.user.UpdateUserResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserControllerDefinition {

    @PostMapping(name = "Update user", value = Routes.USER_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    UpdateUserResponse update(@RequestBody @Valid UpdateUserRequest request);

}
