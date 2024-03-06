package com.sbz.ipfilter.utils;

import com.sbz.ipfilter.domain.model.RuleEntity;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class RuleEntityTestData {
    public static RuleEntity createTestRuleEntity() {
        return RuleEntity.builder()
                .lowerSourceIp("23.12.98.3")
                .upperSourceIp("66.34.87.129")
                .lowerDestinationIp("55.35.74.32")
                .upperDestinationIp("121.34.54.1")
                .allow(true)
                .build();
    }

    public static RuleEntity createTestRuleEntityIncorrectIpFormatByLowerSource() {
        return RuleEntity.builder()
                .lowerSourceIp("23.12.98.we")
                .upperSourceIp("56.1.87.34")
                .lowerDestinationIp("54.255.22.123")
                .upperDestinationIp("70.43.23.75")
                .allow(false)
                .build();
    }

    public static RuleEntity createTestRuleEntityIncorrectIpFormatByUpperSource() {
        return RuleEntity.builder()
                .lowerSourceIp("23.12.98.23")
                .upperSourceIp("cdf.1.87.34")
                .lowerDestinationIp("54.255.22.123")
                .upperDestinationIp("70.43.23.75")
                .allow(false)
                .build();
    }

    public static RuleEntity createTestRuleEntityIncorrectIpFormatByLowerDestination() {
        return RuleEntity.builder()
                .lowerSourceIp("23.12.98.23")
                .upperSourceIp("234.1.87.34")
                .lowerDestinationIp("ef.255.22.123")
                .upperDestinationIp("70.43.23.75")
                .allow(false)
                .build();
    }

    public static RuleEntity createTestRuleEntityIncorrectIpFormatByUpperDestination() {
        return RuleEntity.builder()
                .lowerSourceIp("23.12.98.23")
                .upperSourceIp("234.1.87.34")
                .lowerDestinationIp("23.255.22.123")
                .upperDestinationIp("wq.43.23.75")
                .allow(false)
                .build();
    }


    public static RuleEntity createTestRuleEntityInvalidNumbersRange() {
        return RuleEntity.builder()
                .lowerSourceIp("323.12.98.890")
                .upperSourceIp("124.1.87.67")
                .lowerDestinationIp("54.255.22.123")
                .upperDestinationIp("70.43.23.75")
                .allow(true)
                .build();
    }

    public static RuleEntity createTestRuleEntityWithInvalidRangeBySourceIp() {
        return RuleEntity.builder()
                .lowerSourceIp("125.12.98.34")
                .upperSourceIp("14.1.87.67")
                .lowerDestinationIp("56.255.22.123")
                .upperDestinationIp("1.43.23.75")
                .allow(true)
                .build();
    }

    public static RuleEntity createTestRuleEntityWithInvalidRangeTheSame() {
        return RuleEntity.builder()
                .lowerSourceIp("125.12.98.34")
                .upperSourceIp("125.12.98.34")
                .lowerDestinationIp("56.255.22.123")
                .upperDestinationIp("56.255.22.123")
                .allow(true)
                .build();
    }

    public static RuleEntity createTestRuleEntityWithInvalidRangeByDestinationIp() {
        return RuleEntity.builder()
                .lowerSourceIp("12.12.98.34")
                .upperSourceIp("141.1.87.67")
                .lowerDestinationIp("56.255.22.123")
                .upperDestinationIp("1.43.23.75")
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
