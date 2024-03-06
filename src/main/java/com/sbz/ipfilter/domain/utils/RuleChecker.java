package com.sbz.ipfilter.domain.utils;


import java.util.Deque;

public interface RuleChecker {

    boolean checkIpInRange(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp);

    boolean checkIpRange(Deque<Integer> lowerIp, Deque<Integer> upperIp);

}
