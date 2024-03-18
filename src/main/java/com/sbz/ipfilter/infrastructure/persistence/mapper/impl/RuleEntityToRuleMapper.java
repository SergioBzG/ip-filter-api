package com.sbz.ipfilter.infrastructure.persistence.mapper.impl;

import com.sbz.ipfilter.core.domain.model.Rule;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RuleEntityToRuleMapper implements Mapper<RuleEntity, Rule> {
    private final ModelMapper modelMapper;

    public RuleEntityToRuleMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Rule mapTo(RuleEntity ruleEntity) {
        return modelMapper.map(ruleEntity, Rule.class);
    }

    @Override
    public RuleEntity mapFrom(Rule rule) {
        return modelMapper.map(rule, RuleEntity.class);
    }
}
