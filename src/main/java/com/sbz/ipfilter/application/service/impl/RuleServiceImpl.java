package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.exception.IpFormatException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exception.RuleFormatException;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.core.domain.model.Route;
import com.sbz.ipfilter.core.domain.model.Rule;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
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
    private final Mapper<RuleDto, Rule> ruleDtoToRuleMapper;
    private final Mapper<RuleEntity, RuleDto> ruleEntityToRuleDtoMapper;
    private final Mapper<RuleEntity, Rule> ruleEntityToRuleMapper;

    public RuleServiceImpl(
            final RuleRepository ruleRepository,
            final Mapper<RuleEntity, RuleDto> ruleEntityToRuleDtoMapper,
            final Mapper<RuleDto, Rule> ruleDtoToRuleMapper,
            final Mapper<RuleEntity, Rule> ruleEntityToRuleMapper
    ) {
        this.ruleRepository = ruleRepository;
        this.ruleDtoToRuleMapper = ruleDtoToRuleMapper;
        this.ruleEntityToRuleDtoMapper = ruleEntityToRuleDtoMapper;
        this.ruleEntityToRuleMapper = ruleEntityToRuleMapper;
    }

    @Caching(evict = {
            @CacheEvict(value="rules", allEntries = true),
            @CacheEvict(value="routes", allEntries = true)
    })
    @Override
    public RuleDto save(RuleDto ruleDto) throws IpFormatException, RuleFormatException {
        // Get Rule of domain for validations
        Rule rule = ruleDtoToRuleMapper.mapTo(ruleDto);
        // Check ip
        if(!rule.checkIpFormat())
            // Check ip format
            throw new IpFormatException("Incorrect ip format (e.g. valid format : 123.32.4.212)");
        else if(!rule.checkIpNumbers())
            // Check ip snippet
            throw new IpFormatException("An IP can only has numbers between 0 and 255 (inclusive)");

        // Get raw ips in rule used for validations
        rule.getRawIps();
        if(!rule.checkSourceAndDestinationIpRange())
            // Check that ranges of ips are valid
            throw new RuleFormatException("Invalid ip range");

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
    public void delete(Long id) throws RuleDoesNotExistException  {
        boolean exists = this.ruleRepository.existsById(id);
        if(!exists)
            throw new RuleDoesNotExistException(id);
        this.ruleRepository.deleteById(id);
    }

    @Cacheable(value = "routes")
    @Override
    public boolean checkIpAccess(Route route) throws IpFormatException {
        // Check ips in route
        if(!route.checkIpFormat())
            // Check ip format
            throw new IpFormatException("Incorrect ip format (e.g. valid format : 123.32.4.212)");
        else if(!route.checkIpNumbers())
            // Check ip snippet
            throw new IpFormatException("An IP can only has numbers between 0 and 255 (inclusive)");

        List<RuleEntity> rulesAllowed = this.ruleRepository.findByAllow(true);
        List<Rule> ruleEntitiesAllowed = rulesAllowed.stream()
                .map(this.ruleEntityToRuleMapper::mapTo)
                .toList();

        return ruleEntitiesAllowed.stream()
                .anyMatch(ruleEntity -> {
                    // Get raw ips in ruleEntity used for validations
                    ruleEntity.getRawIps();
                    return ruleEntity.checkSourceIpAccess(route.getSourceIp())
                            && ruleEntity.checkDestinationIpAccess(route.getDestinationIp());
                });
    }
}
