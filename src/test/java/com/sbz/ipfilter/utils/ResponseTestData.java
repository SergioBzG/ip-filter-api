package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.application.utils.Response;

public class ResponseTestData {
    public static Response getInvalidOrMissingDataExceptionResponse() {
        return Response.builder()
                .message("Invalid data in rule")
                .status(false)
                .build();
    }

    public static Response getInvalidIpRangeExceptionResponse() {
        return Response.builder()
                .message("Invalid IP range")
                .status(false)
                .build();
    }

    public static Response getRuleDoesNotExistException() {
        return Response.builder()
                .message("Rule with id 1 does not exist")
                .status(false)
                .build();
    }

    public static Response getException() {
        return Response.builder()
                .message("Fatal error")
                .status(false)
                .build();
    }

}
