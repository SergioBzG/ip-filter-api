package com.sbz.ipfilter.domain.model;

import com.sbz.ipfilter.domain.utils.IpChecker;
import com.sbz.ipfilter.domain.utils.RuleChecker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Deque;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleEntity implements RuleChecker, IpChecker {
    private String lowerSourceIp;
    private String upperSourceIp;
    private String lowerDestinationIp;
    private String upperDestinationIp;
    private Boolean allow;

    public Boolean checkSourceIpAccess(String sourceIp) {
        Deque<Integer> lowerSourceRowIp = this.getRawIp(this.lowerSourceIp);
        Deque<Integer> upperSourceRowIp = this.getRawIp(this.upperSourceIp);
        Deque<Integer> rowIp = this.getRawIp(sourceIp);
        return this.checkIpInRange(rowIp, lowerSourceRowIp, upperSourceRowIp);
    }

    public Boolean checkDestinationIpAccess(String destinationIp) {
        Deque<Integer> lowerDestinationRowIp = this.getRawIp(this.lowerDestinationIp);
        Deque<Integer> upperDestinationRowIp = this.getRawIp(this.upperDestinationIp);
        Deque<Integer> rowIp = this.getRawIp(destinationIp);
        return this.checkIpInRange(rowIp, lowerDestinationRowIp, upperDestinationRowIp);
    }

    public Boolean checkSourceAndDestinationIpRange() {
        Deque<Integer> lowerSourceRowIp = this.getRawIp(this.lowerSourceIp);
        Deque<Integer> upperSourceRowIp = this.getRawIp(this.upperSourceIp);
        Deque<Integer> lowerDestinationRowIp = this.getRawIp(this.lowerDestinationIp);
        Deque<Integer> upperDestinationRowIp = this.getRawIp(this.upperDestinationIp);
        return this.checkIpRange(lowerSourceRowIp, upperSourceRowIp) &&
                this.checkIpRange(lowerDestinationRowIp, upperDestinationRowIp);
    }

    @Override
    public Boolean checkIpInRange(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp) {
        boolean lowerBoundChecked = false;
        boolean upperBoundChecked = false;

        while(!ip.isEmpty()) {
            int firstPartOfIp = ip.pollFirst();
            int firstPartOfLower = lowerIp.pollFirst();
            int firstPartOfUpper = upperIp.pollFirst();

            if(!lowerBoundChecked) {
                if(firstPartOfIp > firstPartOfLower)
                    lowerBoundChecked = true;
                else if (firstPartOfIp < firstPartOfLower)
                    return false;
            }

            if(!upperBoundChecked) {
                if(firstPartOfIp < firstPartOfUpper)
                    upperBoundChecked = true;
                else if (firstPartOfIp > firstPartOfUpper)
                    return false;
            }

            if (upperBoundChecked && lowerBoundChecked)
                return true;
        }
        return true;
    }

    @Override
    public Boolean checkIpFormat() {
        return this.getLowerSourceIp().matches(this.IP_PATTERN)
                && this.getUpperSourceIp().matches(this.IP_PATTERN)
                && this.getLowerDestinationIp().matches(this.IP_PATTERN)
                && this.getUpperDestinationIp().matches(this.IP_PATTERN);
    }

    @Override
    public Boolean checkIpRange(Deque<Integer> lowerIp, Deque<Integer> upperIp) {

        while(!lowerIp.isEmpty()) {
            int firstPartOfLower = lowerIp.pollFirst();
            int firstPartOfUpper = upperIp.pollFirst();

            if(firstPartOfLower < firstPartOfUpper)
                return true;
            else if(firstPartOfLower > firstPartOfUpper)
                return false;
        }
        // In case of the ips are the same
        return false;
    }

    @Override
    public Boolean checkIpNumbers() {
        String allNumberInIps = String.join(
                ".",
                this.lowerSourceIp,
                this.upperSourceIp,
                this.lowerDestinationIp,
                this.upperDestinationIp
        );
        return Arrays.stream(allNumberInIps.split("\\."))
                .map(Integer::valueOf)
                .noneMatch(num -> num < 0 || num > 255);
    }
}
