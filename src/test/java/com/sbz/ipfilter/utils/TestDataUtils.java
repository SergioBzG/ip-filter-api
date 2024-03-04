package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;

public class TestDataUtils {
    public static Rule createTestRuleA() {
        return Rule.builder()
                .id(1L)
                .lowerSourceIp("23.12.98.3")
                .upperSourceIp("66.34.87.129")
                .lowerDestinationIp("55.35.74.32")
                .upperDestinationIp("121.34.54.1")
                .allow(true)
                .build();
    }

    public static Rule createTestRuleB() {
        return Rule.builder()
                .id(2L)
                .lowerSourceIp("123.34.24.255")
                .upperSourceIp("144.234.123.32")
                .lowerDestinationIp("34.123.54.12")
                .upperDestinationIp("245.123.03.0")
                .allow(true)
                .build();
    }

    public static Rule createTestRuleC() {
        return Rule.builder()
                .id(3L)
                .lowerSourceIp("23.12.98.3")
                .upperSourceIp("56.1.87.0")
                .lowerDestinationIp("54.255.47.98")
                .upperDestinationIp("70.43.23.75")
                .allow(false)
                .build();
    }

    public static Rule createTestRuleIncorrectIpFormat() {
        return Rule.builder()
                .id(4L)
                .lowerSourceIp("23.12.98.we")
                .upperSourceIp("56.1.87")
                .lowerDestinationIp("54.255.22.wq")
                .upperDestinationIp("70.43.23.75")
                .allow(false)
                .build();
    }
}

