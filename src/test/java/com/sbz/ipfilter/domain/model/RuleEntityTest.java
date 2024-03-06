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
    void testThatCheckSourceAndDestinationIpRangeReturnsFalseBySourceIp() {
        underTest = RuleEntityTestData.createTestRuleEntityWithInvalidRangeBySourceIp();
        underTest.getRawIps();
        assertFalse(underTest.checkSourceAndDestinationIpRange());
    }

    @Test
    void testThatCheckSourceAndDestinationIpRangeReturnsFalseByDestinationIp() {
        underTest = RuleEntityTestData.createTestRuleEntityWithInvalidRangeByDestinationIp();
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
                        RuleEntityTestData.createRawIp1(),
                        underTest.getLowerSourceRowIp(),
                        underTest.getUpperSourceRowIp()
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsTrueQuickly() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(
                underTest.checkIpInRange(
                        RuleEntityTestData.createRawIp2(),
                        underTest.getLowerSourceRowIp(),
                        underTest.getUpperSourceRowIp()
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsTrueSlowly() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertTrue(
                underTest.checkIpInRange(
                        RuleEntityTestData.createRawIp3(),
                        underTest.getLowerDestinationRowIp(),
                        underTest.getUpperDestinationRowIp()
                )
        );
    }

    @Test
    void testThatCheckIpInRangeReturnsFalse() {
        underTest = RuleEntityTestData.createTestRuleEntity();
        underTest.getRawIps();
        assertFalse(
                underTest.checkIpInRange(
                        RuleEntityTestData.createRawIp1(),
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
    void testThatCheckIpFormatReturnsFalseByLowerSource() {
        underTest = RuleEntityTestData.createTestRuleEntityIncorrectIpFormatByLowerSource();
        assertFalse(underTest.checkIpFormat());
    }

    @Test
    void testThatCheckIpFormatReturnsFalseByUpperSource() {
        underTest = RuleEntityTestData.createTestRuleEntityIncorrectIpFormatByUpperSource();
        assertFalse(underTest.checkIpFormat());
    }
    @Test
    void testThatCheckIpFormatReturnsFalseByLowerDestination() {
        underTest = RuleEntityTestData.createTestRuleEntityIncorrectIpFormatByLowerDestination();
        assertFalse(underTest.checkIpFormat());
    }
    @Test
    void testThatCheckIpFormatReturnsFalseByUpperDestination() {
        underTest = RuleEntityTestData.createTestRuleEntityIncorrectIpFormatByUpperDestination();
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
        underTest = RuleEntityTestData.createTestRuleEntityWithInvalidRangeBySourceIp();
        underTest.getRawIps();
        assertFalse(underTest.checkIpRange(underTest.lowerSourceRowIp, underTest.upperSourceRowIp));
        assertFalse(underTest.checkIpRange(underTest.lowerDestinationRowIp, underTest.upperDestinationRowIp));
    }

    @Test
    void testThatCheckIpRangeReturnsFalseBySameSourceDestination() {
        underTest = RuleEntityTestData.createTestRuleEntityWithInvalidRangeTheSame();
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