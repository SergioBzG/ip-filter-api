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
public class Route implements IpChecker {
    private String sourceIp;
    private String destinationIp;

    @Override
    public Boolean checkIpFormat() {
        return this.sourceIp.matches(this.IP_PATTERN)
                && this.destinationIp.matches(this.IP_PATTERN);
    }

    @Override
    public Boolean checkIpNumbers() {
        String allNumberInIps = String.join(
                ".",
                this.sourceIp,
                this.destinationIp
        );
        return Arrays.stream(allNumberInIps.split("\\."))
                .map(Integer::valueOf)
                .noneMatch(num -> num < 0 || num > 255);
    }
}
