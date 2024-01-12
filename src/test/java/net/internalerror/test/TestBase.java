package net.internalerror.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@Slf4j
@SpringBootTest
public abstract class TestBase extends Assertions {

    protected static final int TEST_RUNS = 7;

    protected static void assertEqualsInstant(Instant a, Instant b) {

        assertEquals(a.getEpochSecond(), b.getEpochSecond());

        // assertEquals(ChronoField.YEAR.getFrom(a),ChronoField.YEAR.getFrom(a));
        // assertEquals(ChronoField.MONTH_OF_YEAR.getFrom(a),ChronoField.MONTH_OF_YEAR.getFrom(a));
        // assertEquals(ChronoField.DAY_OF_MONTH.getFrom(a),ChronoField.DAY_OF_MONTH.getFrom(a));
        // assertEquals(ChronoField.HOUR_OF_DAY.getFrom(a),ChronoField.HOUR_OF_DAY.getFrom(a));
        // assertEquals(ChronoField.MINUTE_OF_HOUR.getFrom(a),ChronoField.MINUTE_OF_HOUR.getFrom(a));
        // assertEquals(ChronoField.SECOND_OF_MINUTE.getFrom(a),ChronoField.SECOND_OF_MINUTE.getFrom(a));
    }

    @BeforeEach
    void logTest(TestInfo testInfo) {
        String clazz = testInfo.getTestClass().orElseThrow().getName();
        String method = testInfo.getTestMethod().orElseThrow().getName();

        log.info("Running test: {}::{}", clazz, method);
    }

}
