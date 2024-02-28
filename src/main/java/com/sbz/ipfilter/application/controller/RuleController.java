package com.sbz.ipfilter.application.controller;

import com.sbz.ipfilter.application.service.IRuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rule")
public class RuleController {

    private final IRuleService ruleService;

    public RuleController(final IRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    public String hello() {
        return "Hola parcerito";
    }
}
