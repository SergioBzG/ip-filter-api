package com.sbz.ipfilter.application.controller;

import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rules")
public class RuleController {

    private final IRuleService ruleService;

    public RuleController(final IRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public ResponseEntity<List<RuleDto>> getRules() {
        List<RuleDto> rules = this.ruleService.findAll();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RuleDto> createRule(@RequestBody RuleDto ruleDto) {
        RuleDto ruleSaved = this.ruleService.save(ruleDto);
        return new ResponseEntity<>(ruleSaved, HttpStatus.CREATED);
    }
}
