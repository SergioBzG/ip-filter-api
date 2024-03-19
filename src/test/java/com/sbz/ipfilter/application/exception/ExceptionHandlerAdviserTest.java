package com.sbz.ipfilter.application.exception;

import com.sbz.ipfilter.application.utils.Response;
import com.sbz.ipfilter.utils.ResponseTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerAdviserTest {
    private ExceptionHandlerAdviser underTest;
    @BeforeEach
    void setUp() {
        underTest = new ExceptionHandlerAdviser();
    }
    @Test
    void testThatInvalidOrMissingDataExceptionHandlerReturnsAResponse() {
        // Data for test
        Response invalidOrMissingDataExceptionResponse = ResponseTestData.getInvalidOrMissingDataExceptionResponse();
        InvalidOrMissingDataException exception = new InvalidOrMissingDataException(
                "Invalid data in rule"
        );
        ResponseEntity<Response> response = underTest.invalidOrMissingDataExceptionHandler(exception);
        // Asserts
        assertNotNull(response);
        assertEquals(
                HttpStatus.BAD_REQUEST, response.getStatusCode()
        );
        assertEquals(
                invalidOrMissingDataExceptionResponse, response.getBody()
        );
    }

    @Test
    void testThatInvalidIpRangeExceptionHandlerReturnsAResponse() {
        // Data for test
        Response InvalidIpRangeExceptionResponse = ResponseTestData.getInvalidIpRangeExceptionResponse();
        InvalidIpRangeException exception = new InvalidIpRangeException(
                "Invalid IP range"
        );
        ResponseEntity<Response> response = underTest.invalidIpRangeExceptionHandler(exception);
        // Asserts
        assertNotNull(response);
        assertEquals(
                HttpStatus.BAD_REQUEST, response.getStatusCode()
        );
        assertEquals(
                InvalidIpRangeExceptionResponse, response.getBody()
        );
    }

    @Test
    void testThatRuleDoesNotExistExceptionHandlerReturnsAResponse() {
        // Data for test
        Response ruleDoesNotExistExceptionResponse = ResponseTestData.getRuleDoesNotExistException();
        RuleDoesNotExistException exception = new RuleDoesNotExistException(1L);
        ResponseEntity<Response> response = underTest.ruleDoesNotExistExceptionHandler(exception);
        // Asserts
        assertNotNull(response);
        assertEquals(
                HttpStatus.NOT_FOUND, response.getStatusCode()
        );
        assertEquals(
                ruleDoesNotExistExceptionResponse, response.getBody()
        );
    }

    @Test
    void exceptionHandler() {
        // Data for test
        Response exceptionOrMissingDataExceptionResponse = ResponseTestData.getException();
        Exception exception = new Exception(
                "Fatal error"
        );
        ResponseEntity<Response> response = underTest.exceptionHandler(exception);
        // Asserts
        assertNotNull(response);
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()
        );
        assertEquals(
                exceptionOrMissingDataExceptionResponse, response.getBody()
        );
    }
}