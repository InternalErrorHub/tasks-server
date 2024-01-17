package net.internalerror.rest.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.ServerUtil;
import net.internalerror.data.repository.UserRepository;
import net.internalerror.rest.Messages;
import net.internalerror.rest.definition.AuthControllerDefinition;
import net.internalerror.rest.exception.ValidationException;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.rest.response.auth.LoginResponse;
import net.internalerror.rest.response.auth.RegisterResponse;
import net.internalerror.rest.security.SecurityService;
import net.internalerror.rest.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController implements AuthControllerDefinition {

  private final AuthService authService;

  private final UserRepository userRepository;

  private final SecurityService securityService;

  @Override
  public RegisterResponse register(RegisterRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new ValidationException(Messages.EMAIL_IS_UNAVAILABLE);
    }

    ServerUtil.checkPasswordStrength(request.getPassword());

    return authService.register(request);
  }

  @Override
  public LoginResponse login(LoginRequest request) {

    if (!userRepository.existsByEmail(request.getEmail())) {
      throw new ValidationException(Messages.EMAIL_IS_UNREGISTERED);
    }

    if (!securityService.passwordMatches(request.getEmail(), request.getPassword())) {
      throw new ValidationException(Messages.INVALID_CREDENTIALS);
    }

    return authService.login(request);
  }

}
