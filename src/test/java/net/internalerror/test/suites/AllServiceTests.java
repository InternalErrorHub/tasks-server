package net.internalerror.test.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("net.internalerror.rest.service")
public class AllServiceTests {
}
