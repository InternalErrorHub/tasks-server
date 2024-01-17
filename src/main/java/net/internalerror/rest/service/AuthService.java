package net.internalerror.rest.service;

import lombok.RequiredArgsConstructor;
import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.entity.User;
import net.internalerror.data.repository.UserRepository;
import net.internalerror.rest.definition.AuthControllerDefinition;
import net.internalerror.rest.request.auth.LoginRequest;
import net.internalerror.rest.request.auth.RegisterRequest;
import net.internalerror.rest.response.auth.LoginResponse;
import net.internalerror.rest.response.auth.RegisterResponse;
import net.internalerror.rest.security.SecurityService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthControllerDefinition {

  private final SecurityService securityService;

  private final UserRepository userRepository;

  @Override
  public RegisterResponse register(RegisterRequest request) {

    User user = new User();
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    user.setEmail(request.getEmail());
    user.setPassword(securityService.encodePassword(request.getPassword()));

    userRepository.save(user);

    return new RegisterResponse(user.getEmail());
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail());
    SecurityToken securityToken = securityService.getSecurityToken(user);

    return new LoginResponse(securityToken.getToken());
  }

}
