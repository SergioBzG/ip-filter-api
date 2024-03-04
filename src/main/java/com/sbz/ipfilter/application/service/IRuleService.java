package com.sbz.ipfilter.application.service;

import com.sbz.ipfilter.application.exceptions.IpFormatException;
import com.sbz.ipfilter.application.exceptions.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exceptions.RuleFormatException;
import com.sbz.ipfilter.domain.model.Route;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRuleService {

    RuleDto save(RuleDto ruleDto) throws IpFormatException, RuleFormatException;

    Page<RuleDto> findAll(Pageable pageable);

    void delete(Long id) throws RuleDoesNotExistException;

    boolean checkIpAccess(Route route) throws IpFormatException;
}
