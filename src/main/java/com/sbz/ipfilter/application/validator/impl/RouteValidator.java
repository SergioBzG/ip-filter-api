package com.sbz.ipfilter.application.validator.impl;

import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.application.validator.IpChecker;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

@Component
public class RouteValidator implements IpChecker {
    @Override
    public void checkInvalidOrMissingData(BindingResult errors) {
        if(errors.hasErrors()){
            throw new InvalidOrMissingDataException(errors.getAllErrors().get(0).getDefaultMessage());
        }
    }

    public void checkIpNumbersInRoute(RouteDto routeDto) {
        String allNumbersInIps = String.join(
                ".",
                routeDto.getSourceIp(),
                routeDto.getDestinationIp()
        );
        boolean error =  Arrays.stream(allNumbersInIps.split("\\."))
                .map(Integer::valueOf)
                .anyMatch(num -> num > 255);
        if(error)
            throw new InvalidOrMissingDataException("An IP can only has numbers between 0 and 255 (inclusive)");
    }

}
