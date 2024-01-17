package net.internalerror.test;

import lombok.extern.slf4j.Slf4j;
import net.internalerror.data.repository.SecurityTokenRepository;
import net.internalerror.data.repository.TaskRepository;
import net.internalerror.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class CleanDatabaseTestBase extends TestBase {

  @Autowired
  protected DataUtil dataUtil;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected SecurityTokenRepository securityTokenRepository;

  @Autowired
  protected TaskRepository taskRepository;

  @BeforeEach
  public void cleanDatabase() {
    log.debug("Cleaning Database for new Test execution");
    taskRepository.deleteAll();
    securityTokenRepository.deleteAll();
    userRepository.deleteAll();
  }

}
