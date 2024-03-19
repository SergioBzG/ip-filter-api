package com.sbz.ipfilter.core.domain.model;

import com.sbz.ipfilter.utils.RuleTestData;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RuleTest {
    private Rule underTest;
    @Test
    void testThatCheckSourceIpAccessReturnsTrue() {
        underTest = RuleTestData.createTestRuleA();
        assertTrue(underTest.checkSourceIpAccess(underTest.getSourceRange().getSourceIp()));
    }

    @Test
    void testThatCheckSourceIpAccessReturnsFalse() {
        underTest = RuleTestData.createTestRuleA();
        assertFalse(underTest.checkSourceIpAccess("500.3.123.41"));
    }

    @Test
    void testThatCheckDestinationIpAccessReturnsTrue() {
        underTest = RuleTestData.createTestRuleA();
        assertTrue(underTest.checkDestinationIpAccess(underTest.getDestinationRange().getSourceIp()));
    }

    @Test
    void testThatCheckDestinationIpAccessReturnsFalse() {
        underTest = RuleTestData.createTestRuleA();
        assertFalse(underTest.checkDestinationIpAccess("500.3.123.41"));
    }

    @Test
    void testThatCheckIpInRangeReturnsTrue() {
        underTest = RuleTestData.createTestRuleA();
        assertTrue(
                underTest.checkIpInRange(
                        RuleTestData.createRawIp1(),
                        underTest.getRawIp(underTest.getSourceRange().getSourceIp()),
                        underTest.getRawIp(underTest.getSourceRange().getDestinationIp())
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsTrueQuickly() {
        underTest = RuleTestData.createTestRuleA();
        assertTrue(
                underTest.checkIpInRange(
                        RuleTestData.createRawIp2(),
                        underTest.getRawIp(underTest.getSourceRange().getSourceIp()),
                        underTest.getRawIp(underTest.getSourceRange().getDestinationIp())
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsTrueSlowly() {
        underTest = RuleTestData.createTestRuleA();
        assertTrue(
                underTest.checkIpInRange(
                        RuleTestData.createRawIp3(),
                        underTest.getRawIp(underTest.getDestinationRange().getSourceIp()),
                        underTest.getRawIp(underTest.getDestinationRange().getDestinationIp())
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsFalse() {
        underTest = RuleTestData.createTestRuleA();
        assertFalse(
                underTest.checkIpInRange(
                        RuleTestData.createRawIp1(),
                        underTest.getRawIp(underTest.getDestinationRange().getSourceIp()),
                        underTest.getRawIp(underTest.getDestinationRange().getDestinationIp())
                )
        );
    }

}