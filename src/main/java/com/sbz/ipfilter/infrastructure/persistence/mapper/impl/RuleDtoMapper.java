package com.sbz.ipfilter.infrastructure.persistence.mapper.impl;

import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RuleDtoMapper implements Mapper<Rule, RuleDto> {

    private final ModelMapper modelMapper;

    public RuleDtoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RuleDto mapTo(Rule rule) {
        return this.modelMapper.map(rule, RuleDto.class);
    }

    @Override
    public Rule mapFrom(RuleDto ruleDto) {
        return this.modelMapper.map(ruleDto, Rule.class);
    }
}
