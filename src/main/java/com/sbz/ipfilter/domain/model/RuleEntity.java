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
    Deque<Integer> lowerSourceRowIp;
    Deque<Integer> upperSourceRowIp;
    Deque<Integer> lowerDestinationRowIp;
    Deque<Integer> upperDestinationRowIp;

    public RuleEntity(String lowerSourceIp, String upperSourceIp, String lowerDestinationIp, String upperDestinationIp, Boolean allow) {
        this.lowerSourceIp = lowerSourceIp;
        this.upperSourceIp = upperSourceIp;
        this.lowerDestinationIp = lowerDestinationIp;
        this.upperDestinationIp = upperDestinationIp;
        this.allow = allow;
    }

    public Boolean checkSourceIpAccess(String sourceIp) {
        Deque<Integer> rowIp = this.getRawIp(sourceIp);
        return this.checkIpInRange(rowIp, this.lowerSourceRowIp, this.upperSourceRowIp);
    }

    public Boolean checkDestinationIpAccess(String destinationIp) {
        Deque<Integer> rowIp = this.getRawIp(destinationIp);
        return this.checkIpInRange(rowIp, this.lowerDestinationRowIp, this.upperDestinationRowIp);
    }

    public Boolean checkSourceAndDestinationIpRange() {
        return this.checkIpRange(this.lowerSourceRowIp, this.upperSourceRowIp) &&
                this.checkIpRange(this.lowerDestinationRowIp, this.upperDestinationRowIp);
    }

    public void getRawIps() {
        this.lowerSourceRowIp = this.getRawIp(this.lowerSourceIp);
        this.upperSourceRowIp = this.getRawIp(this.upperSourceIp);
        this.lowerDestinationRowIp = this.getRawIp(this.lowerDestinationIp);
        this.upperDestinationRowIp = this.getRawIp(this.upperDestinationIp);
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
