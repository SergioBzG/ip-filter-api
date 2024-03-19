package com.sbz.ipfilter.application.validator.impl;

import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.utils.RouteDtoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RouteValidatorTest {
    private RouteValidator underTest;
    @BeforeEach
    void setUp() {
        underTest = new RouteValidator();
    }
    @Test
    void testThatCheckInvalidOrMissingDataThrowsInvalidOrMissingDataException() {
        // Data for test
        BindingResult errors = mock(BindingResult.class);
        ObjectError objectError = new ObjectError("RouteDto", "Invalid or missing data");
        when(errors.hasErrors()).thenReturn(true);
        when(errors.getAllErrors()).thenReturn(List.of(objectError));
        // Check invalid or missing data
        Exception exception = assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.checkInvalidOrMissingData(errors)
        );
        // Assertions
        assertNotNull(exception);
        assertEquals(
                "Invalid or missing data",
                exception.getMessage()
        );
    }

    @Test
    void testThatCheckInvalidOrMissingDataDoesNothing() {
        // Data for test
        BindingResult errors = mock(BindingResult.class);
        when(errors.hasErrors()).thenReturn(false);
        // Check invalid or missing data
        underTest.checkInvalidOrMissingData(errors);
        // Assertions
        verify(errors, times(1)).hasErrors();
    }

    @Test
    void testThatCheckIpNumbersInRuleThrowsInvalidOrMissingDataException() {
        // Data for test
        RouteDto routeDto = RouteDtoTestData.createRouteDtoInvalidNumbersRange();
        // Check ip numbers in rule
        Exception exception = assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.checkIpNumbersInRoute(routeDto)
        );
        // Assertions
        assertNotNull(exception);
        assertEquals(
                "An IP can only has numbers between 0 and 255 (inclusive)",
                exception.getMessage()
        );
    }
}