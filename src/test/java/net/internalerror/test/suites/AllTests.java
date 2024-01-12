package net.internalerror.test.suites;

import net.internalerror.TasksServer;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Suite
@SelectClasses({
        AllControllerTests.class,
        AllServiceTests.class,
        AllRequestTests.class
})
class AllTests {
    @Test
    void test() {
        assertDoesNotThrow(() -> TasksServer.main(new String[]{}));
    }
}
