package com.sbz.ipfilter.infrastructure.persistence.mapper.impl;

import com.sbz.ipfilter.domain.model.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RuleToRuleEntityMapper implements Mapper<Rule, RuleEntity> {
    private final ModelMapper modelMapper;

    public RuleToRuleEntityMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RuleEntity mapTo(Rule rule) {
        return modelMapper.map(rule, RuleEntity.class);
    }

    @Override
    public Rule mapFrom(RuleEntity ruleEntity) {
        return modelMapper.map(ruleEntity, Rule.class);
    }
}
