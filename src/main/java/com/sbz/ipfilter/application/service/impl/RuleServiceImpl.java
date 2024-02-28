package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl implements IRuleService {

    private final Mapper<Rule, RuleEntity> ruleMapper;

    public RuleServiceImpl(final Mapper<Rule, RuleEntity> ruleMapper) {
        this.ruleMapper = ruleMapper;
    }
}
