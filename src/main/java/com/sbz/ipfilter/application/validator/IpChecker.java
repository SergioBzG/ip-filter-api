package com.sbz.ipfilter.application.validator;

import com.sbz.ipfilter.application.dto.RuleDto;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public interface IpChecker {
    void checkInvalidOrMissingData(BindingResult errors);
}
