package com.sbz.ipfilter.infrastructure.persistence.mapper.impl;

import com.sbz.ipfilter.domain.model.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RuleDtoToRuleEntityMapperImpl implements Mapper<RuleDto, RuleEntity> {

    private final ModelMapper modelMapper;

    public RuleDtoToRuleEntityMapperImpl(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RuleEntity mapTo(RuleDto ruleDto) {
        return this.modelMapper.map(ruleDto, RuleEntity.class);
    }

    @Override
    public RuleDto mapFrom(RuleEntity ruleEntity) {
        return this.modelMapper.map(ruleEntity, RuleDto.class);
    }
}
