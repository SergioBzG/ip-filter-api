package com.sbz.ipfilter.core.mapper.impl;

import com.sbz.ipfilter.core.domain.model.Route;
import com.sbz.ipfilter.core.mapper.Mapper;
import com.sbz.ipfilter.core.domain.model.Rule;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RuleEntityToRuleMapper implements Mapper<RuleEntity, Rule> {

    @Override
    public Rule mapTo(RuleEntity ruleEntity) {
        return Rule.builder()
                .sourceRange(
                        Route.builder()
                                .sourceIp(ruleEntity.getLowerSourceIp())
                                .destinationIp(ruleEntity.getUpperSourceIp())
                                .build()
                )
                .destinationRange(
                        Route.builder()
                                .sourceIp(ruleEntity.getLowerDestinationIp())
                                .destinationIp(ruleEntity.getUpperDestinationIp())
                                .build()
                )
                .allow(ruleEntity.getAllow())
                .build();
    }

    @Override
    public RuleEntity mapFrom(Rule rule) {
        return RuleEntity.builder()
                .lowerSourceIp(rule.getSourceRange().getSourceIp())
                .upperSourceIp(rule.getSourceRange().getDestinationIp())
                .lowerDestinationIp(rule.getDestinationRange().getSourceIp())
                .upperDestinationIp(rule.getDestinationRange().getDestinationIp())
                .allow(rule.getAllow())
                .build();
    }
}
