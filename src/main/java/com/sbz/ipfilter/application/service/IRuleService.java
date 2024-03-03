package com.sbz.ipfilter.application.service;

import com.sbz.ipfilter.domain.model.Route;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;

import java.util.List;

public interface IRuleService {

    RuleDto save(RuleDto ruleDto);

    List<RuleDto> findAll();

    void delete(Long id);

    boolean checkIpAccess(Route route);
}
