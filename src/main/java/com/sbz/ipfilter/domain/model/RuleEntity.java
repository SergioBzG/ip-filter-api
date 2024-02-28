package com.sbz.ipfilter.domain.model;

import com.sbz.ipfilter.domain.utils.Ipv4;
import com.sbz.ipfilter.domain.utils.RuleChecker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Deque;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleEntity implements RuleChecker, Ipv4 {
    private String lowerSourceIp;
    private String upperSourceIp;
    private String lowerDestinationIp;
    private String upperDestinationIp;
    private Boolean allow;


    public Boolean checkSourceIpAccess(String sourceIp) {
        Deque<Integer> lowerSourceRowIp = this.getRawIp(this.lowerSourceIp);
        Deque<Integer> upperSourceRowIp = this.getRawIp(this.upperSourceIp);
        Deque<Integer> rowIp = this.getRawIp(sourceIp);
        return this.checkIpAccess(rowIp, lowerSourceRowIp, upperSourceRowIp);
    }

    public Boolean checkDestinationIpAccess(String destinationIp) {
        Deque<Integer> lowerDestinationRowIp = this.getRawIp(this.lowerDestinationIp);
        Deque<Integer> upperDestinationRowIp = this.getRawIp(this.upperDestinationIp);
        Deque<Integer> rowIp = this.getRawIp(destinationIp);
        return this.checkIpAccess(rowIp, lowerDestinationRowIp, upperDestinationRowIp);
    }

    @Override
    public Boolean checkIpAccess(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp) {
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
        }
        return true;
    }

    @Override
    public Boolean checkIpRuleFormat() {
        return this.getLowerSourceIp().matches(this.IP_PATTERN)
                && this.getUpperSourceIp().matches(this.IP_PATTERN)
                && this.getLowerDestinationIp().matches(this.IP_PATTERN)
                && this.getUpperDestinationIp().matches(this.IP_PATTERN);
    }
}
