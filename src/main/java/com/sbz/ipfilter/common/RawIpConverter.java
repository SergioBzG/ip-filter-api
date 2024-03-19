package com.sbz.ipfilter.common;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public interface RawIpConverter {
    default Deque<Integer> getRawIp(String ip) {
        Deque<Integer> rawIp = new LinkedList<>();
        Arrays.stream(ip.split("\\."))
                .forEach(str -> rawIp.add(Integer.valueOf(str)));
        return rawIp;
    }
}
