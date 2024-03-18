package com.sbz.ipfilter.core.usecase.utils;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public interface IpChecker {
    String IP_PATTERN = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";

    boolean checkIpFormat();

    boolean checkIpNumbers();

    default Deque<Integer> getRawIp(String ip) {
        Deque<Integer> rawIp = new LinkedList<>();
        Arrays.stream(ip.split("\\."))
                .forEach(str -> rawIp.add(Integer.valueOf(str)));
        return rawIp;
    }
}
