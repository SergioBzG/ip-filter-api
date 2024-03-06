package com.sbz.ipfilter.domain.model;

import com.sbz.ipfilter.domain.utils.IpChecker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteEntity implements IpChecker {
    private String sourceIp;
    private String destinationIp;

    @Override
    public boolean checkIpFormat() {
        return this.sourceIp.matches(IP_PATTERN)
                && this.destinationIp.matches(IP_PATTERN);
    }

    @Override
    public boolean checkIpNumbers() {
        String allNumberInIps = String.join(
                ".",
                this.sourceIp,
                this.destinationIp
        );
        return Arrays.stream(allNumberInIps.split("\\."))
                .map(Integer::valueOf)
                .noneMatch(num -> num > 255);
    }
}
