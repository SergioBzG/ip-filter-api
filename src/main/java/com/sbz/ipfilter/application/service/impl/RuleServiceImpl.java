package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.exception.IpFormatException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exception.RuleFormatException;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.RouteEntity;
import com.sbz.ipfilter.domain.model.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import com.sbz.ipfilter.infrastructure.persistence.repository.RuleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RuleServiceImpl implements IRuleService {

    private final RuleRepository ruleRepository;
    private final Mapper<RuleDto, RuleEntity> ruleDtoToRuleEntityMapper;
    private final Mapper<Rule, RuleDto> ruleToRuleDtoMapper;
    private final Mapper<Rule, RuleEntity> ruleToRuleEntityMapper;

    public RuleServiceImpl(
            final RuleRepository ruleRepository,
            final Mapper<Rule, RuleDto> ruleToRuleDtoMapper,
            final Mapper<RuleDto, RuleEntity> ruleDtoToRuleEntityMapper,
            final Mapper<Rule, RuleEntity> ruleToRuleEntityMapper
    ) {
        this.ruleRepository = ruleRepository;
        this.ruleDtoToRuleEntityMapper = ruleDtoToRuleEntityMapper;
        this.ruleToRuleDtoMapper = ruleToRuleDtoMapper;
        this.ruleToRuleEntityMapper = ruleToRuleEntityMapper;
    }

    @Caching(evict = {
            @CacheEvict(value="rules", allEntries = true),
            @CacheEvict(value="routes", allEntries = true)
    })
    @Override
    public RuleDto save(RuleDto ruleDto) throws IpFormatException, RuleFormatException {
        // Get RuleEntity of domain to validations
        RuleEntity ruleEntity = ruleDtoToRuleEntityMapper.mapTo(ruleDto);
        // Check ip
        if(!ruleEntity.checkIpFormat())
            // Check ip format
            throw new IpFormatException("Incorrect ip format (e.g. valid format : 123.32.4.212)");
        else if(!ruleEntity.checkIpNumbers())
            // Check ip snippet
            throw new IpFormatException("An IP can only has numbers between 0 and 255 (inclusive)");

        // Get raw ips in ruleEntity used for validations
        ruleEntity.getRawIps();
        if(!ruleEntity.checkSourceAndDestinationIpRange())
            // Check that ranges of ips are valid
            throw new RuleFormatException("Invalid ip range");

        Rule ruleSaved = this.ruleRepository.save(
                this.ruleToRuleDtoMapper.mapFrom(ruleDto)
        );
        return this.ruleToRuleDtoMapper.mapTo(ruleSaved);
    }

    @Cacheable(value = "rules", sync = true)
    @Override
    public Page<RuleDto> findAll(Pageable pageable) {
        Page<Rule> rulesPage = this.ruleRepository.findAll(pageable);
        return rulesPage.map(this.ruleToRuleDtoMapper::mapTo);
    }

    @Caching(evict = {
            @CacheEvict(value="rules", allEntries = true),
            @CacheEvict(value="routes", allEntries = true)
    })
    @Override
    public void delete(Long id) throws RuleDoesNotExistException  {
        boolean exists = this.ruleRepository.existsById(id);
        if(!exists)
            throw new RuleDoesNotExistException(id);
        this.ruleRepository.deleteById(id);
    }

    @Cacheable(value = "routes")
    @Override
    public boolean checkIpAccess(RouteEntity routeEntity) throws IpFormatException {
        // Check ips in route
        if(!routeEntity.checkIpFormat())
            // Check ip format
            throw new IpFormatException("Incorrect ip format (e.g. valid format : 123.32.4.212)");
        else if(!routeEntity.checkIpNumbers())
            // Check ip snippet
            throw new IpFormatException("An IP can only has numbers between 0 and 255 (inclusive)");

        List<Rule> rulesAllowed = this.ruleRepository.findByAllow(true);
        List<RuleEntity> ruleEntitiesAllowed = rulesAllowed.stream()
                .map(this.ruleToRuleEntityMapper::mapTo)
                .toList();

        return ruleEntitiesAllowed.stream()
                .anyMatch(ruleEntity -> {
                    // Get raw ips in ruleEntity used for validations
                    ruleEntity.getRawIps();
                    return ruleEntity.checkSourceIpAccess(routeEntity.getSourceIp())
                            && ruleEntity.checkDestinationIpAccess(routeEntity.getDestinationIp());
                });
    }
}
