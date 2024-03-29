package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.application.dto.RuleDto;

public class RuleDtoTestData {
    public static RuleDto createTestRuleDtoA() {
        return RuleDto.builder()
                .id(1L)
                .lowerSourceIp("23.12.98.3")
                .upperSourceIp("66.34.87.129")
                .lowerDestinationIp("55.35.74.32")
                .upperDestinationIp("121.34.54.1")
                .allow(true)
                .build();
    }

    public static RuleDto createTestRuleDtoB() {
        return RuleDto.builder()
                .id(2L)
                .lowerSourceIp("123.34.24.255")
                .upperSourceIp("144.234.123.32")
                .lowerDestinationIp("34.123.54.12")
                .upperDestinationIp("245.123.03.0")
                .allow(true)
                .build();
    }

    public static RuleDto createTestRuleDtoWithNumbersOutOfRange() {
        return RuleDto.builder()
                .id(3L)
                .lowerSourceIp("455.34.24.255")
                .upperSourceIp("144.234.123.32")
                .lowerDestinationIp("34.123.54.12")
                .upperDestinationIp("245.123.03.0")
                .allow(true)
                .build();
    }

    public static RuleDto createTestRuleDtoWithInvalidSourceRange() {
        return RuleDto.builder()
                .id(3L)
                .lowerSourceIp("123.34.24.255")
                .upperSourceIp("12.234.123.32")
                .lowerDestinationIp("34.123.54.12")
                .upperDestinationIp("245.123.03.0")
                .allow(true)
                .build();
    }

    public static RuleDto createTestRuleDtoWithInvalidDestinationRange() {
        return RuleDto.builder()
                .id(3L)
                .lowerSourceIp("12.34.24.255")
                .upperSourceIp("144.234.123.32")
                .lowerDestinationIp("34.123.54.12")
                .upperDestinationIp("1.123.03.0")
                .allow(true)
                .build();
    }

    public static RuleDto createTestRuleDtoWithInvalidRange() {
        return RuleDto.builder()
                .id(3L)
                .lowerSourceIp("122.34.24.255")
                .upperSourceIp("14.234.123.32")
                .lowerDestinationIp("34.123.54.12")
                .upperDestinationIp("1.123.03.0")
                .allow(true)
                .build();
    }
}
