package com.sbz.ipfilter.domain.utils;


import java.util.Deque;


public interface RuleChecker {

    Boolean checkIpAccess(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp);

    Boolean checkIpRuleFormat();
}
