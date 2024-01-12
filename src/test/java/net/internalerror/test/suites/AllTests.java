package net.internalerror.test.suites;

import lombok.Generated;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Generated
@SelectClasses({
        AllControllerTests.class,
        AllServiceTests.class,
        AllRequestTests.class
})
public class AllTests {
}
