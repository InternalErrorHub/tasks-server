package net.internalerror.rest.service;

import lombok.RequiredArgsConstructor;
import net.internalerror.data.entity.User;
import net.internalerror.data.repository.UserRepository;
import net.internalerror.rest.definition.UserControllerDefinition;
import net.internalerror.rest.request.user.UpdateUserRequest;
import net.internalerror.rest.response.user.UpdateUserResponse;
import net.internalerror.rest.security.SecurityService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserControllerDefinition {

    private final SecurityService securityService;

    private final UserRepository userRepository;

    @Override
    public UpdateUserResponse update(UpdateUserRequest request) {
        User user = securityService.getUser(request);

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());

        userRepository.save(user);

        return new UpdateUserResponse(user.getFirstname(), user.getLastname());
    }

}
