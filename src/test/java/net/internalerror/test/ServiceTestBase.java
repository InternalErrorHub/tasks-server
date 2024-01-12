package net.internalerror.test;

import lombok.AccessLevel;
import lombok.Getter;
import net.internalerror.rest.service.AuthService;
import net.internalerror.rest.service.TaskService;
import net.internalerror.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Getter(AccessLevel.PROTECTED)
public abstract class ServiceTestBase extends CleanDatabaseTestBase {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

}
