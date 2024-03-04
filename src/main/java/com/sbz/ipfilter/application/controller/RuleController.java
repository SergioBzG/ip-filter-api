package com.sbz.ipfilter.application.controller;

import com.sbz.ipfilter.application.exceptions.IpFormatException;
import com.sbz.ipfilter.application.exceptions.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exceptions.RuleFormatException;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.Route;
import com.sbz.ipfilter.application.utils.Response;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
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
    public ResponseEntity<Page<RuleDto>> getRules(Pageable pageable) {
        Page<RuleDto> rules = this.ruleService.findAll(pageable);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createRule(@RequestBody RuleDto ruleDto) {
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
    public ResponseEntity<Response> checkIp(@RequestBody Route route) {
        boolean allow;
        try {
            allow = this.ruleService.checkIpAccess(route);
        } catch (IpFormatException e) {
            return new ResponseEntity<>(new Response(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
        if(allow)
            return new ResponseEntity<>(new Response("Allowed access", true), HttpStatus.OK);
        return new ResponseEntity<>(new Response("Denied access", true), HttpStatus.OK);
    }

}
