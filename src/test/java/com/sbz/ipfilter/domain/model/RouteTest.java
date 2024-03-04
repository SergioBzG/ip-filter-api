package com.sbz.ipfilter.domain.model;

import com.sbz.ipfilter.utils.RouteTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {
    private Route underTest;

    @Test
    void testThatCheckIpFormatReturnsTrue() {
        underTest = RouteTestData.createRoute();
        assertTrue(underTest.checkIpFormat());
    }

    @Test
    void testThatCheckIpFormatReturnsFalse() {
        underTest = RouteTestData.createRouteInvalidFormat();
        assertFalse(underTest.checkIpFormat());
    }

    @Test
    void testThatCheckIpNumbersReturnsTrue() {
        underTest = RouteTestData.createRoute();
        assertTrue(underTest.checkIpNumbers());
    }

    @Test
    void testThatCheckIpNumbersReturnsFalse() {
        underTest = RouteTestData.createRouteInvalidNumbersRange();
        assertFalse(underTest.checkIpNumbers());
    }
}