package com.sbz.ipfilter.domain.model;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public interface RuleChecker {

    int DEFAULT_IP_SIZE = 4;
    Boolean checkIpAccess(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp);

    default Deque<Integer> getRawIp(String ip) {
        Deque<Integer> rawIp = new LinkedList<>();
        Arrays.stream(ip.split("\\."))
                .forEach(str -> rawIp.add(Integer.valueOf(str)));
        return rawIp;
    }
}
