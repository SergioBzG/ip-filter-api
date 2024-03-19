package com.sbz.ipfilter.core.service.impl;

import com.sbz.ipfilter.core.service.IRuleService;
import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.core.domain.model.Route;
import com.sbz.ipfilter.core.domain.model.Rule;
import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.core.mapper.Mapper;
import com.sbz.ipfilter.infrastructure.persistence.repository.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class RuleServiceImpl implements IRuleService {

    private final RuleRepository ruleRepository;
    private final Mapper<RuleEntity, RuleDto> ruleEntityToRuleDtoMapper;
    private final Mapper<RuleEntity, Rule> ruleEntityToRuleMapper;

    @Caching(evict = {
            @CacheEvict(value="rules", allEntries = true),
            @CacheEvict(value="routes", allEntries = true)
    })
    @Override
    public RuleDto save(RuleDto ruleDto) {
        RuleEntity ruleEntitySaved = this.ruleRepository.save(
                this.ruleEntityToRuleDtoMapper.mapFrom(ruleDto)
        );
        return this.ruleEntityToRuleDtoMapper.mapTo(ruleEntitySaved);
    }

    @Cacheable(value = "rules", sync = true)
    @Override
    public Page<RuleDto> findAll(Pageable pageable) {
        Page<RuleEntity> rulesPage = this.ruleRepository.findAll(pageable);
        return rulesPage.map(this.ruleEntityToRuleDtoMapper::mapTo);
    }

    @Caching(evict = {
            @CacheEvict(value="rules", allEntries = true),
            @CacheEvict(value="routes", allEntries = true)
    })
    @Override
    public void delete(Long id) {
        boolean exists = this.ruleRepository.existsById(id);
        if(!exists)
            throw new RuleDoesNotExistException(id);
        this.ruleRepository.deleteById(id);
    }

    @Cacheable(value = "routes")
    @Override
    public boolean checkIpAccess(RouteDto routeDto) throws InvalidOrMissingDataException {
        List<RuleEntity> rulesEntitiesAllowed = this.ruleRepository.findByAllow(true);
        List<Rule> rulesAllowed = rulesEntitiesAllowed.stream()
                .map(this.ruleEntityToRuleMapper::mapTo)
                .toList();

        return rulesAllowed.stream()
                .anyMatch(rule -> rule.checkSourceIpAccess(routeDto.getSourceIp())
                        && rule.checkDestinationIpAccess(routeDto.getDestinationIp())
                );
    }
}
