package com.sbz.ipfilter.core.domain.model;

import com.sbz.ipfilter.common.RawIpConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Deque;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rule implements RawIpConverter {
    private Route sourceRange;
    private Route destinationRange;
    private Boolean allow;

    public boolean checkSourceIpAccess(String sourceIp) {
        return this.checkIpInRange(
                this.getRawIp(sourceIp),
                this.getRawIp(this.sourceRange.getSourceIp()),
                this.getRawIp(this.sourceRange.getDestinationIp())
        );
    }

    public boolean checkDestinationIpAccess(String destinationIp) {
        return this.checkIpInRange(
                this.getRawIp(destinationIp),
                this.getRawIp(this.destinationRange.getSourceIp()),
                this.getRawIp(this.destinationRange.getDestinationIp())
        );
    }

    public boolean checkIpInRange(Deque<Integer> ip, Deque<Integer> lowerIp, Deque<Integer> upperIp) {
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
}
