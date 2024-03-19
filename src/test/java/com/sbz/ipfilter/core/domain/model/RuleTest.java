//package com.sbz.ipfilter.core.domain.model;
//
//import com.sbz.ipfilter.utils.RuleTestData;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RuleTest {
//    private Rule underTest;
//    @Test
//    void testThatCheckSourceIpAccessReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(underTest.checkSourceIpAccess(underTest.getLowerSourceIp()));
//    }
//
//    @Test
//    void testThatCheckSourceIpAccessReturnsFalse() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertFalse(underTest.checkSourceIpAccess("500.3.123.41"));
//    }
//
//    @Test
//    void testThatCheckDestinationIpAccessReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(underTest.checkDestinationIpAccess(underTest.getLowerDestinationIp()));
//    }
//
//    @Test
//    void testThatCheckDestinationIpAccessReturnsFalse() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertFalse(underTest.checkDestinationIpAccess("500.3.123.41"));
//    }
//
//    @Test
//    void testThatCheckSourceAndDestinationIpRangeReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(underTest.checkSourceAndDestinationIpRange());
//    }
//
//    @Test
//    void testThatCheckSourceAndDestinationIpRangeReturnsFalseBySourceIp() {
//        underTest = RuleTestData.createTestRuleWithInvalidRangeBySourceIp();
//        underTest.getRawIps();
//        assertFalse(underTest.checkSourceAndDestinationIpRange());
//    }
//
//    @Test
//    void testThatCheckSourceAndDestinationIpRangeReturnsFalseByDestinationIp() {
//        underTest = RuleTestData.createTestRuleWithInvalidRangeByDestinationIp();
//        underTest.getRawIps();
//        assertFalse(underTest.checkSourceAndDestinationIpRange());
//    }
//
//    @Test
//    void testThatGetRawIpsConvertStringIpInRowIp() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertEquals(
//                Arrays.stream(underTest.getLowerSourceIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
//                underTest.getLowerSourceRowIp().stream().mapToInt(Integer::valueOf).sum()
//        );
//        assertEquals(
//                Arrays.stream(underTest.getUpperSourceIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
//                underTest.getUpperSourceRowIp().stream().mapToInt(Integer::valueOf).sum()
//        );
//        assertEquals(
//                Arrays.stream(underTest.getLowerDestinationIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
//                underTest.getLowerDestinationRowIp().stream().mapToInt(Integer::valueOf).sum()
//        );
//        assertEquals(
//                Arrays.stream(underTest.getUpperDestinationIp().split("\\.")).mapToInt(Integer::valueOf).sum(),
//                underTest.getUpperDestinationRowIp().stream().mapToInt(Integer::valueOf).sum()
//        );
//    }
//
//    @Test
//    void testThatCheckIpInRangeReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(
//                underTest.checkIpInRange(
//                        RuleTestData.createRawIp1(),
//                        underTest.getLowerSourceRowIp(),
//                        underTest.getUpperSourceRowIp()
//                )
//        );
//    }
//
//    @Test
//    void testThatCheckIpInRangeReturnsTrueQuickly() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(
//                underTest.checkIpInRange(
//                        RuleTestData.createRawIp2(),
//                        underTest.getLowerSourceRowIp(),
//                        underTest.getUpperSourceRowIp()
//                )
//        );
//    }
//
//    @Test
//    void testThatCheckIpInRangeReturnsTrueSlowly() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(
//                underTest.checkIpInRange(
//                        RuleTestData.createRawIp3(),
//                        underTest.getLowerDestinationRowIp(),
//                        underTest.getUpperDestinationRowIp()
//                )
//        );
//    }
//
//    @Test
//    void testThatCheckIpInRangeReturnsFalse() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertFalse(
//                underTest.checkIpInRange(
//                        RuleTestData.createRawIp1(),
//                        underTest.getLowerDestinationRowIp(),
//                        underTest.getUpperDestinationRowIp()
//                )
//        );
//    }
//
//    @Test
//    void testThatCheckIpFormatReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        assertTrue(underTest.checkIpFormat());
//    }
//
//    @Test
//    void testThatCheckIpFormatReturnsFalseByLowerSource() {
//        underTest = RuleTestData.createTestRuleIncorrectIpFormatByLowerSource();
//        assertFalse(underTest.checkIpFormat());
//    }
//
//    @Test
//    void testThatCheckIpFormatReturnsFalseByUpperSource() {
//        underTest = RuleTestData.createTestRuleIncorrectIpFormatByUpperSource();
//        assertFalse(underTest.checkIpFormat());
//    }
//    @Test
//    void testThatCheckIpFormatReturnsFalseByLowerDestination() {
//        underTest = RuleTestData.createTestRuleIncorrectIpFormatByLowerDestination();
//        assertFalse(underTest.checkIpFormat());
//    }
//    @Test
//    void testThatCheckIpFormatReturnsFalseByUpperDestination() {
//        underTest = RuleTestData.createTestRuleIncorrectIpFormatByUpperDestination();
//        assertFalse(underTest.checkIpFormat());
//    }
//
//    @Test
//    void testThatCheckIpRangeReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        underTest.getRawIps();
//        assertTrue(underTest.checkIpRange(underTest.getLowerSourceRowIp(), underTest.getUpperSourceRowIp()));
//        assertTrue(underTest.checkIpRange(underTest.getLowerDestinationRowIp(), underTest.getUpperDestinationRowIp()));
//    }
//
//    @Test
//    void testThatCheckIpRangeReturnsFalse() {
//        underTest = RuleTestData.createTestRuleWithInvalidRangeBySourceIp();
//        underTest.getRawIps();
//        assertFalse(underTest.checkIpRange(underTest.getLowerSourceRowIp(), underTest.getUpperSourceRowIp()));
//        assertFalse(underTest.checkIpRange(underTest.getLowerDestinationRowIp(), underTest.getUpperDestinationRowIp()));
//    }
//
//    @Test
//    void testThatCheckIpRangeReturnsFalseBySameSourceDestination() {
//        underTest = RuleTestData.createTestRuleWithInvalidRangeTheSame();
//        underTest.getRawIps();
//        assertFalse(underTest.checkIpRange(underTest.getLowerSourceRowIp(), underTest.getUpperSourceRowIp()));
//        assertFalse(underTest.checkIpRange(underTest.getLowerDestinationRowIp(), underTest.getUpperDestinationRowIp()));
//    }
//
//    @Test
//    void testThatCheckIpNumbersReturnsTrue() {
//        underTest = RuleTestData.createTestRule();
//        assertTrue(underTest.checkIpNumbers());
//    }
//    @Test
//    void testThatCheckIpNumbersReturnsFalse() {
//        underTest = RuleTestData.createTestRuleInvalidNumbersRange();
//        assertFalse(underTest.checkIpNumbers());
//    }
//}