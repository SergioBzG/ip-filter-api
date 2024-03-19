package com.sbz.ipfilter.application.validator.impl;

import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.application.exception.InvalidIpRangeException;
import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.application.validator.IpChecker;
import com.sbz.ipfilter.common.RawIpConverter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Deque;

@Component
public class RuleValidator implements IpChecker, RawIpConverter {
    @Override
    public void checkInvalidOrMissingData(BindingResult errors) {
        if(errors.hasErrors()){
            throw new InvalidOrMissingDataException(errors.getAllErrors().get(0).getDefaultMessage());
        }
    }

    public void checkIpNumbersInRule(RuleDto ruleDto) {
        String allNumbersInIps = String.join(
                ".",
                ruleDto.getLowerSourceIp(),
                ruleDto.getUpperSourceIp(),
                ruleDto.getLowerDestinationIp(),
                ruleDto.getUpperDestinationIp()
        );
        boolean error =  Arrays.stream(allNumbersInIps.split("\\."))
                .map(Integer::valueOf)
                .anyMatch(num -> num > 255);
        if(error)
            throw new InvalidOrMissingDataException("An IP can only has numbers between 0 and 255 (inclusive)");
    }

    public void checkSourceAndDestinationIpRange(RuleDto ruleDto) {
        // Get raw ips
        Deque<Integer> lowerSourceRowIp = this.getRawIp(ruleDto.getLowerSourceIp());
        Deque<Integer> upperSourceRowIp = this.getRawIp(ruleDto.getUpperSourceIp());
        Deque<Integer> lowerDestinationRowIp = this.getRawIp(ruleDto.getLowerDestinationIp());
        Deque<Integer> upperDestinationRowIp = this.getRawIp(ruleDto.getUpperDestinationIp());

        if(!this.checkIpRange(lowerSourceRowIp, upperSourceRowIp)
                || !this.checkIpRange(lowerDestinationRowIp, upperDestinationRowIp))
            throw new InvalidIpRangeException("Invalid IP range");
    }

    private boolean checkIpRange(Deque<Integer> lowerIp, Deque<Integer> upperIp) {
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
}
