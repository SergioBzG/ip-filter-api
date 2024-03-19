package com.sbz.ipfilter.application.controller;

import com.sbz.ipfilter.application.validator.impl.RouteValidator;
import com.sbz.ipfilter.application.validator.impl.RuleValidator;
import com.sbz.ipfilter.core.service.IRuleService;
import com.sbz.ipfilter.application.utils.Response;
import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.application.dto.RuleDto;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/rules")
public class RuleController {

    private final IRuleService ruleService;
    private final RuleValidator ruleValidator;
    private final RouteValidator routeValidator;

    @GetMapping
    public ResponseEntity<Page<RuleDto>> getRules(@ParameterObject Pageable pageable) {
        Page<RuleDto> rules = this.ruleService.findAll(pageable);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RuleDto> createRule(@Validated @RequestBody RuleDto ruleDto, BindingResult errors) {
        // Validations
        this.ruleValidator.checkInvalidOrMissingData(errors);
        this.ruleValidator.checkIpNumbersInRule(ruleDto);
        this.ruleValidator.checkSourceAndDestinationIpRange(ruleDto);
        return new ResponseEntity<>(this.ruleService.save(ruleDto), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable("id") Long id) {
        ruleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/check")
    public ResponseEntity<Response> checkIp(@Validated @RequestBody RouteDto routeDto, BindingResult errors) {
        this.routeValidator.checkInvalidOrMissingData(errors);
        this.routeValidator.checkIpNumbersInRoute(routeDto);
        boolean allow = this.ruleService.checkIpAccess(routeDto);
        if(allow)
            return new ResponseEntity<>(new Response("Allowed access", true), HttpStatus.OK);
        return new ResponseEntity<>(new Response("Denied access", true), HttpStatus.OK);
    }

}
