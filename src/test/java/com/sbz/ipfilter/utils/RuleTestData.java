package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.core.domain.model.Route;
import com.sbz.ipfilter.core.domain.model.Rule;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class RuleTestData {
    public static Rule createTestRuleA() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("23.12.98.3")
                                .destinationIp("66.34.87.129")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("55.35.74.32")
                                .destinationIp("121.34.54.1")
                                .build()
                )
                .allow(true)
                .build();
    }

    public static Rule createTestRuleIncorrectIpFormatByLowerSource() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("23.12.98.we")
                                .destinationIp("56.1.87.34")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("54.255.22.123")
                                .destinationIp("70.43.23.75")
                                .build()
                )
                .allow(false)
                .build();
    }

    public static Rule createTestRuleIncorrectIpFormatByUpperSource() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("23.12.98.23")
                                .destinationIp("cdf.1.87.34")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("54.255.22.123")
                                .destinationIp("70.43.23.75")
                                .build()
                )
                .allow(false)
                .build();
    }

    public static Rule createTestRuleIncorrectIpFormatByLowerDestination() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("23.12.98.23")
                                .destinationIp("234.1.87.34")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("ef.255.22.123")
                                .destinationIp("70.43.23.75")
                                .build()
                )
                .allow(false)
                .build();
    }

    public static Rule createTestRuleIncorrectIpFormatByUpperDestination() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("23.12.98.23")
                                .destinationIp("234.1.87.34")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("23.255.22.123")
                                .destinationIp("wq.43.23.75")
                                .build()
                )
                .build();
    }


    public static Rule createTestRuleInvalidNumbersRange() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("323.12.98.890")
                                .destinationIp("124.1.87.67")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("54.255.22.123")
                                .destinationIp("70.43.23.75")
                                .build()
                )
                .allow(true)
                .build();
    }

    public static Rule createTestRuleWithInvalidRangeBySourceIp() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("125.12.98.34")
                                .destinationIp("14.1.87.67")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("56.255.22.123")
                                .destinationIp("1.43.23.75")
                                .build()
                )
                .allow(true)
                .build();
    }

    public static Rule createTestRuleWithInvalidRangeTheSame() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("125.12.98.34")
                                .destinationIp("125.12.98.34")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("56.255.22.123")
                                .destinationIp("56.255.22.123")
                                .build()
                )
                .allow(true)
                .build();
    }

    public static Rule createTestRuleWithInvalidRangeByDestinationIp() {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp("12.12.98.34")
                                .destinationIp("141.1.87.67")
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp("56.255.22.123")
                                .destinationIp("1.43.23.75")
                                .build()
                )
                .allow(true)
                .build();
    }

    public static Deque<Integer> createRawIp1() {
        return new LinkedList<>(Arrays.asList(23,12,98,3));
    }

    public static Deque<Integer> createRawIp2() {
        return new LinkedList<>(Arrays.asList(30,12,98,3));
    }

    public static Deque<Integer> createRawIp3() {
        return new LinkedList<>(Arrays.asList(55,35,74,32));
    }

}
