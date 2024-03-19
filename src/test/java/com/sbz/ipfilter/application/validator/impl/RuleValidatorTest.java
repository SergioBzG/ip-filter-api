package com.sbz.ipfilter.application.validator.impl;

import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.application.exception.InvalidIpRangeException;
import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.utils.RuleDtoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleValidatorTest {
    private RuleValidator underTest;
    @BeforeEach
    void setUp() {
        underTest = new RuleValidator();
    }
    @Test
    void testThatCheckInvalidOrMissingDataThrowsInvalidOrMissingDataException() {
        // Data for test
        BindingResult errors = mock(BindingResult.class);
        ObjectError objectError = new ObjectError("RuleDto", "Invalid or missing data");
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
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoWithNumbersOutOfRange();
        // Check ip numbers in rule
        Exception exception = assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.checkIpNumbersInRule(ruleDto)
        );
        // Assertions
        assertNotNull(exception);
        assertEquals(
                "An IP can only has numbers between 0 and 255 (inclusive)",
                exception.getMessage()
        );
    }

    @Test
    void testThatCheckSourceAndDestinationIpRangeTrowsInvalidIpRangeExceptionBySourceRange() {
        // Data for test
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoWithInvalidSourceRange();
        // Check source and destination ip range
        Exception exception = assertThrows(
                InvalidIpRangeException.class,
                () -> underTest.checkSourceAndDestinationIpRange(ruleDto)
        );
        // Asserts
        assertNotNull(exception);
        assertEquals(
                "Invalid IP range",
                exception.getMessage()
        );
    }

    @Test
    void testThatCheckSourceAndDestinationIpRangeTrowsInvalidIpRangeExceptionByDestinationRange() {
        // Data for test
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoWithInvalidDestinationRange();
        // Check source and destination ip range
        Exception exception = assertThrows(
                InvalidIpRangeException.class,
                () -> underTest.checkSourceAndDestinationIpRange(ruleDto)
        );
        // Asserts
        assertNotNull(exception);
        assertEquals(
                "Invalid IP range",
                exception.getMessage()
        );
    }

    @Test
    void testThatCheckSourceAndDestinationIpRangeTrowsInvalidIpRangeExceptionBySourceAndDestinationRage() {
        // Data for test
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoWithInvalidRange();
        // Check source and destination ip range
        Exception exception = assertThrows(
                InvalidIpRangeException.class,
                () -> underTest.checkSourceAndDestinationIpRange(ruleDto)
        );
        // Asserts
        assertNotNull(exception);
        assertEquals(
                "Invalid IP range",
                exception.getMessage()
        );
    }
}