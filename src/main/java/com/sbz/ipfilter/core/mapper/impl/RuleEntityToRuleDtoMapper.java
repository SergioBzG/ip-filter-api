package com.sbz.ipfilter.core.mapper.impl;

import com.sbz.ipfilter.core.mapper.Mapper;
import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RuleEntityToRuleDtoMapper implements Mapper<RuleEntity, RuleDto> {

    private final ModelMapper modelMapper;

    public RuleEntityToRuleDtoMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RuleDto mapTo(RuleEntity ruleEntity) {
        return this.modelMapper.map(ruleEntity, RuleDto.class);
    }

    @Override
    public RuleEntity mapFrom(RuleDto ruleDto) {
        return this.modelMapper.map(ruleDto, RuleEntity.class);
    }
}
