package com.sbz.ipfilter.domain.utils;


import java.util.Deque;

public interface RuleChecker {

    Boolean checkIpInRange(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp);

    Boolean checkIpRange(Deque<Integer> lowerIp, Deque<Integer> upperIp);

}
