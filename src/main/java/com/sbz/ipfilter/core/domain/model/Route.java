package com.sbz.ipfilter.core.domain.model;

import com.sbz.ipfilter.core.usecase.utils.IpChecker;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "a source ip is required")
    private String sourceIp;

    @NotBlank(message = "a destination ip is required")
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
