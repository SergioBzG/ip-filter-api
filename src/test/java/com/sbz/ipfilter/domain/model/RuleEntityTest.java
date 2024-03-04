package com.sbz.ipfilter.domain.model;

import com.sbz.ipfilter.utils.RuleEntityTestData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RuleEntityTest {
    private RuleEntity underTest;
    @Test
    void testThatCheckSourceIpAccessReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(underTest.checkSourceIpAccess(underTest.getLowerSourceIp()));
    }

    @Test
    void testThatCheckSourceIpAccessReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertFalse(underTest.checkSourceIpAccess("500.3.123.41"));
    }

    @Test
    void testThatCheckDestinationIpAccessReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(underTest.checkDestinationIpAccess(underTest.getLowerDestinationIp()));
    }

    @Test
    void testThatCheckDestinationIpAccessReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertFalse(underTest.checkDestinationIpAccess("500.3.123.41"));
    }

    @Test
    void testThatCheckSourceAndDestinationIpRangeReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(underTest.checkSourceAndDestinationIpRange());
    }

    @Test
    void testThatCheckSourceAndDestinationIpRangeReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntityWithInvalidRange();
        underTest.getRawIps();
        assertFalse(underTest.checkSourceAndDestinationIpRange());
    }

    @Test
    void testThatGetRawIpsConvertStringIpInRowIp() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertEquals(
                Arrays.stream(underTest.getLowerSourceIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
                underTest.getLowerSourceRowIp().stream().mapToInt(Integer::valueOf).sum()
        );
        assertEquals(
                Arrays.stream(underTest.getUpperSourceIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
                underTest.getUpperSourceRowIp().stream().mapToInt(Integer::valueOf).sum()
        );
        assertEquals(
                Arrays.stream(underTest.getLowerDestinationIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
                underTest.getLowerDestinationRowIp().stream().mapToInt(Integer::valueOf).sum()
        );
        assertEquals(
                Arrays.stream(underTest.getUpperDestinationIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
                underTest.getUpperDestinationRowIp().stream().mapToInt(Integer::valueOf).sum()
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(
                underTest.checkIpInRange(
                        RuleEntityTestData.createRawIp(),
                        underTest.getLowerSourceRowIp(),
                        underTest.getUpperSourceRowIp()
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertFalse(
                underTest.checkIpInRange(
                        RuleEntityTestData.createRawIp(),
                        underTest.getLowerDestinationRowIp(),
                        underTest.getUpperDestinationRowIp()
                )
        );
    }

    @Test
    void testThatCheckIpFormatReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        assertTrue(underTest.checkIpFormat());
    }

    @Test
    void testThatCheckIpFormatReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntityIncorrectIpFormat();
        assertFalse(underTest.checkIpFormat());
    }

    @Test
    void testThatCheckIpRangeReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(underTest.checkIpRange(underTest.lowerSourceRowIp, underTest.upperSourceRowIp));
        assertTrue(underTest.checkIpRange(underTest.lowerDestinationRowIp, underTest.upperDestinationRowIp));
    }

    @Test
    void testThatCheckIpRangeReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntityWithInvalidRange();
        underTest.getRawIps();
        assertFalse(underTest.checkIpRange(underTest.lowerSourceRowIp, underTest.upperSourceRowIp));
        assertFalse(underTest.checkIpRange(underTest.lowerDestinationRowIp, underTest.upperDestinationRowIp));
    }

    @Test
    void testThatCheckIpNumbersReturnsTrue() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        assertTrue(underTest.checkIpNumbers());
    }
    @Test
    void testThatCheckIpNumbersReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntityInvalidNumbersRange();
        assertFalse(underTest.checkIpNumbers());
    }
}