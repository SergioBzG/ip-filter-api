package com.sbz.ipfilter.core.service;

import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exception.InvalidIpRangeException;
import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.application.dto.RuleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRuleService {

    RuleDto save(RuleDto ruleDto);

    Page<RuleDto> findAll(Pageable pageable);

    void delete(Long id);

    boolean checkIpAccess(RouteDto routeDto);
}
