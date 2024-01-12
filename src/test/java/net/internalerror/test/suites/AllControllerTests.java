package net.internalerror.test.suites;

import net.internalerror.TasksServer;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Suite
@SelectPackages("net.internalerror.rest.controller")
public class AllControllerTests {

    @Test
    void test() {
        assertDoesNotThrow(() -> TasksServer.main(new String[]{}));
    }

}
