//package com.sbz.ipfilter.core.domain.model;
//
//import com.sbz.ipfilter.utils.RouteTestData;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RouteTest {
//    private Route underTest;
//
//    @Test
//    void testThatCheckIpFormatReturnsTrue() {
//        underTest = RouteTestData.createRoute();
//        assertTrue(underTest.checkIpFormat());
//    }
//
//    @Test
//    void testThatCheckIpFormatReturnsFalseBySourceIp() {
//        underTest = RouteTestData.createRouteInvalidFormatSourceIp();
//        assertFalse(underTest.checkIpFormat());
//    }
//
//    @Test
//    void testThatCheckIpFormatReturnsFalseByDestinationIp() {
//        underTest = RouteTestData.createRouteInvalidFormatDestinationIp();
//        assertFalse(underTest.checkIpFormat());
//    }
//
//    @Test
//    void testThatCheckIpNumbersReturnsTrue() {
//        underTest = RouteTestData.createRoute();
//        assertTrue(underTest.checkIpNumbers());
//    }
//
//    @Test
//    void testThatCheckIpNumbersReturnsFalse() {
//        underTest = RouteTestData.createRouteInvalidNumbersRange();
//        assertFalse(underTest.checkIpNumbers());
//    }
//}