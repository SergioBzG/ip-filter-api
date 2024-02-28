package com.sbz.ipfilter.application.service;

import com.sbz.ipfilter.domain.model.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;

import java.util.List;

public interface IRuleService {

    RuleDto save(RuleDto ruleDto);

    List<RuleDto> findAll();
}
