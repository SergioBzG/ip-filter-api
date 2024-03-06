package com.sbz.ipfilter.application.controller;

import com.sbz.ipfilter.application.exception.IpFormatException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exception.RuleFormatException;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.RouteEntity;
import com.sbz.ipfilter.application.utils.Response;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rules")
public class RuleController {

    private final IRuleService ruleService;

    public RuleController(final IRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public ResponseEntity<Page<RuleDto>> getRules(@ParameterObject Pageable pageable) {
        Page<RuleDto> rules = this.ruleService.findAll(pageable);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createRule(@Valid @RequestBody RuleDto ruleDto) {
        RuleDto ruleSaved;
        try {
            ruleSaved = this.ruleService.save(ruleDto);
        } catch (IpFormatException | RuleFormatException e) {
            return new ResponseEntity<>(new Response(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ruleSaved, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteRule(@PathVariable("id") Long id) {
        try {
            ruleService.delete(id);
        } catch (RuleDoesNotExistException e) {
            return new ResponseEntity<>(new Response(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/check")
    public ResponseEntity<Response> checkIp(@Valid @RequestBody RouteEntity routeEntity) {
        boolean allow;
        try {
            allow = this.ruleService.checkIpAccess(routeEntity);
        } catch (IpFormatException e) {
            return new ResponseEntity<>(new Response(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
        if(allow)
            return new ResponseEntity<>(new Response("Allowed access", true), HttpStatus.OK);
        return new ResponseEntity<>(new Response("Denied access", true), HttpStatus.OK);
    }

}
