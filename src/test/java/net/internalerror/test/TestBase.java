package net.internalerror.test;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public abstract class TestBase extends Assertions {

  protected static final int TEST_RUNS = 7;

  protected static void assertEqualsInstant(Instant a, Instant b) {
    assertEquals(a.getEpochSecond(), b.getEpochSecond());
  }

  @BeforeEach
  void logTest(TestInfo testInfo) {
    String clazz = testInfo.getTestClass()
                           .orElseThrow()
                           .getName();
    String method = testInfo.getTestMethod()
                            .orElseThrow()
                            .getName();
    log.debug("Running test: {}::{}", clazz, method);
  }

}
