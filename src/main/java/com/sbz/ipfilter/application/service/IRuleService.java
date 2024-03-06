package com.sbz.ipfilter.application.service;

import com.sbz.ipfilter.application.exception.IpFormatException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exception.RuleFormatException;
import com.sbz.ipfilter.domain.model.RouteEntity;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRuleService {

    RuleDto save(RuleDto ruleDto) throws IpFormatException, RuleFormatException;

    Page<RuleDto> findAll(Pageable pageable);

    void delete(Long id) throws RuleDoesNotExistException;

    boolean checkIpAccess(RouteEntity routeEntity) throws IpFormatException;
}
