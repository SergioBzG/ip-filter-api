//package com.sbz.ipfilter.utils;
//
//import com.sbz.ipfilter.core.domain.model.Rule;
//
//import java.util.Arrays;
//import java.util.Deque;
//import java.util.LinkedList;
//
//public class RuleTestData {
//    public static Rule createTestRule() {
//        return Rule.builder()
//                .lowerSourceIp("23.12.98.3")
//                .upperSourceIp("66.34.87.129")
//                .lowerDestinationIp("55.35.74.32")
//                .upperDestinationIp("121.34.54.1")
//                .allow(true)
//                .build();
//    }
//
//    public static Rule createTestRuleIncorrectIpFormatByLowerSource() {
//        return Rule.builder()
//                .lowerSourceIp("23.12.98.we")
//                .upperSourceIp("56.1.87.34")
//                .lowerDestinationIp("54.255.22.123")
//                .upperDestinationIp("70.43.23.75")
//                .allow(false)
//                .build();
//    }
//
//    public static Rule createTestRuleIncorrectIpFormatByUpperSource() {
//        return Rule.builder()
//                .lowerSourceIp("23.12.98.23")
//                .upperSourceIp("cdf.1.87.34")
//                .lowerDestinationIp("54.255.22.123")
//                .upperDestinationIp("70.43.23.75")
//                .allow(false)
//                .build();
//    }
//
//    public static Rule createTestRuleIncorrectIpFormatByLowerDestination() {
//        return Rule.builder()
//                .lowerSourceIp("23.12.98.23")
//                .upperSourceIp("234.1.87.34")
//                .lowerDestinationIp("ef.255.22.123")
//                .upperDestinationIp("70.43.23.75")
//                .allow(false)
//                .build();
//    }
//
//    public static Rule createTestRuleIncorrectIpFormatByUpperDestination() {
//        return Rule.builder()
//                .lowerSourceIp("23.12.98.23")
//                .upperSourceIp("234.1.87.34")
//                .lowerDestinationIp("23.255.22.123")
//                .upperDestinationIp("wq.43.23.75")
//                .allow(false)
//                .build();
//    }
//
//
//    public static Rule createTestRuleInvalidNumbersRange() {
//        return Rule.builder()
//                .lowerSourceIp("323.12.98.890")
//                .upperSourceIp("124.1.87.67")
//                .lowerDestinationIp("54.255.22.123")
//                .upperDestinationIp("70.43.23.75")
//                .allow(true)
//                .build();
//    }
//
//    public static Rule createTestRuleWithInvalidRangeBySourceIp() {
//        return Rule.builder()
//                .lowerSourceIp("125.12.98.34")
//                .upperSourceIp("14.1.87.67")
//                .lowerDestinationIp("56.255.22.123")
//                .upperDestinationIp("1.43.23.75")
//                .allow(true)
//                .build();
//    }
//
//    public static Rule createTestRuleWithInvalidRangeTheSame() {
//        return Rule.builder()
//                .lowerSourceIp("125.12.98.34")
//                .upperSourceIp("125.12.98.34")
//                .lowerDestinationIp("56.255.22.123")
//                .upperDestinationIp("56.255.22.123")
//                .allow(true)
//                .build();
//    }
//
//    public static Rule createTestRuleWithInvalidRangeByDestinationIp() {
//        return Rule.builder()
//                .lowerSourceIp("12.12.98.34")
//                .upperSourceIp("141.1.87.67")
//                .lowerDestinationIp("56.255.22.123")
//                .upperDestinationIp("1.43.23.75")
//                .allow(true)
//                .build();
//    }
//
//    public static Deque<Integer> createRawIp1() {
//        return new LinkedList<>(Arrays.asList(23,12,98,3));
//    }
//
//    public static Deque<Integer> createRawIp2() {
//        return new LinkedList<>(Arrays.asList(30,12,98,3));
//    }
//
//    public static Deque<Integer> createRawIp3() {
//        return new LinkedList<>(Arrays.asList(55,35,74,32));
//    }
//
//}
