package com.sbz.ipfilter.infrastructure.persistence.mapper.impl;

import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
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
