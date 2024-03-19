package com.sbz.ipfilter.utils;


import com.sbz.ipfilter.application.dto.RouteDto;

public class RouteDtoTestData {
    public static RouteDto createRouteDtoA() {
        return RouteDto.builder()
                .sourceIp("23.12.98.3")
                .destinationIp("55.35.74.32")
                .build();
    }

    public static RouteDto createRouteDtoB() {
        return RouteDto.builder()
                .sourceIp("123.12.98.3")
                .destinationIp("244.35.74.32")
                .build();
    }

    public static RouteDto createRouteDtoInvalidFormatSourceIp() {
        return RouteDto.builder()
                .sourceIp("23.12.98.er")
                .destinationIp("55ed.35.74.32")
                .build();
    }

    public static RouteDto createRouteDtoInvalidFormatDestinationIp() {
        return RouteDto.builder()
                .sourceIp("23.12.98.45")
                .destinationIp("55ed.35.74.32")
                .build();
    }

    public static RouteDto createRouteDtoInvalidNumbersRange() {
        return RouteDto.builder()
                .sourceIp("345.12.98.3")
                .destinationIp("1.35.74.32")
                .build();
    }
}
