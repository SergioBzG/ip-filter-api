package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.core.domain.model.Route;

public class RouteTestData {
    public static Route createRoute() {
        return Route.builder()
                .sourceIp("23.12.98.3")
                .destinationIp("55.35.74.32")
                .build();
    }

    public static Route createRouteInvalidFormatSourceIp() {
        return Route.builder()
                .sourceIp("23.12.98.er")
                .destinationIp("55ed.35.74.32")
                .build();
    }

    public static Route createRouteInvalidFormatDestinationIp() {
        return Route.builder()
                .sourceIp("23.12.98.45")
                .destinationIp("55ed.35.74.32")
                .build();
    }

    public static Route createRouteInvalidNumbersRange() {
        return Route.builder()
                .sourceIp("345.12.98.3")
                .destinationIp("1.35.74.32")
                .build();
    }
}
