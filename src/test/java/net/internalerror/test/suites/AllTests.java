package net.internalerror.test.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AllControllerTests.class, AllServiceTests.class, AllRequestTests.class})
class AllTests {

}
