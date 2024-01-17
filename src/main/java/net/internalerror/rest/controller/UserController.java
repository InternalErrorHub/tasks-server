package net.internalerror.rest.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.rest.definition.UserControllerDefinition;
import net.internalerror.rest.request.user.UpdateUserRequest;
import net.internalerror.rest.response.user.UpdateUserResponse;
import net.internalerror.rest.security.SecurityService;
import net.internalerror.rest.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController implements UserControllerDefinition {

  private final UserService userService;

  private final SecurityService securityService;

  @Override
  public UpdateUserResponse update(UpdateUserRequest request) {
    securityService.checkToken(request);
    return userService.update(request);
  }

}
