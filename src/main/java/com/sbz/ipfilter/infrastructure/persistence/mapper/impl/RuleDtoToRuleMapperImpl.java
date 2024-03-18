package com.sbz.ipfilter.infrastructure.persistence.mapper.impl;

import com.sbz.ipfilter.core.domain.model.Rule;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RuleDtoToRuleMapperImpl implements Mapper<RuleDto, Rule> {

    private final ModelMapper modelMapper;

    public RuleDtoToRuleMapperImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Rule mapTo(RuleDto ruleDto) {
        return this.modelMapper.map(ruleDto, Rule.class);
    }

    @Override
    public RuleDto mapFrom(Rule rule) {
        return this.modelMapper.map(rule, RuleDto.class);
    }
}
