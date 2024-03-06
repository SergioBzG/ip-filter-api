package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.domain.model.RouteEntity;

public class RouteTestData {
    public static RouteEntity createRoute() {
        return RouteEntity.builder()
                .sourceIp("23.12.98.3")
                .destinationIp("55.35.74.32")
                .build();
    }

    public static RouteEntity createRouteInvalidFormatSourceIp() {
        return RouteEntity.builder()
                .sourceIp("23.12.98.er")
                .destinationIp("55ed.35.74.32")
                .build();
    }

    public static RouteEntity createRouteInvalidFormatDestinationIp() {
        return RouteEntity.builder()
                .sourceIp("23.12.98.45")
                .destinationIp("55ed.35.74.32")
                .build();
    }

    public static RouteEntity createRouteInvalidNumbersRange() {
        return RouteEntity.builder()
                .sourceIp("345.12.98.3")
                .destinationIp("1.35.74.32")
                .build();
    }
}
