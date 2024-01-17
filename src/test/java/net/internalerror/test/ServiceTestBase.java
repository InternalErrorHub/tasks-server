package net.internalerror.test;

import net.internalerror.rest.service.AuthService;
import net.internalerror.rest.service.TaskService;
import net.internalerror.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ServiceTestBase extends CleanDatabaseTestBase {

  @Autowired
  protected AuthService authService;

  @Autowired
  protected UserService userService;

  @Autowired
  protected TaskService taskService;

}
