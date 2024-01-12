package net.internalerror.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.data.repository.SecurityTokenRepository;
import net.internalerror.data.repository.TaskRepository;
import net.internalerror.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Getter(AccessLevel.PROTECTED)
public abstract class CleanDatabaseTestBase extends TestBase {

    @Autowired
    private DataUtil dataUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void cleanDatabase() {
        log.info("Cleaning Database for new Test execution");
        taskRepository.deleteAll();
        securityTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

}
