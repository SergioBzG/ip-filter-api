package com.sbz.ipfilter.domain.model;

import lombok.Data;

import java.util.Deque;

@Data
public class RuleEntity implements RuleChecker {
    private Long id;
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

}
