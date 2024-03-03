package com.sbz.ipfilter.application.service;

import com.sbz.ipfilter.domain.model.Route;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRuleService {

    RuleDto save(RuleDto ruleDto);

    Page<RuleDto> findAll(Pageable pageable);

    void delete(Long id);

    boolean checkIpAccess(Route route);
}
