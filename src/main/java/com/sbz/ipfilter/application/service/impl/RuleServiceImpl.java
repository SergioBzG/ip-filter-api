package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.Route;
import com.sbz.ipfilter.domain.model.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import com.sbz.ipfilter.infrastructure.persistence.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

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

    @Override
    public RuleDto save(RuleDto ruleDto) {
        // Get RuleEntity of domain to validations
        RuleEntity ruleEntity = ruleDtoToRuleEntityMapper.mapTo(ruleDto);
        // Check ip
        if(!ruleEntity.checkIpFormat())
            // Check ip format
            throw new IllegalStateException("Incorrect ip format");
        else if(!ruleEntity.checkIpNumbers())
            // Check ip snippet
            throw new IllegalStateException("An IP can only has numbers between 0 and 255 (inclusive)");
        else if(!ruleEntity.checkSourceAndDestinationIpRange())
            // Check that ranges of ips are valid
            throw new IllegalStateException("Invalid ip range");

        Rule ruleSaved = this.ruleRepository.save(
                this.ruleToRuleDtoMapper.mapFrom(ruleDto)
        );
        return this.ruleToRuleDtoMapper.mapTo(ruleSaved);
    }

    @Override
    public List<RuleDto> findAll() {
        Iterable<Rule> rulesIterable = this.ruleRepository.findAll();
        return StreamSupport.stream(rulesIterable.spliterator(), false)
                .map(this.ruleToRuleDtoMapper::mapTo)
                .toList();
    }

    @Override
    public void delete(Long id) {
        boolean exists = this.ruleRepository.existsById(id);
        if(!exists)
            throw new IllegalStateException("Rule with id " + id + " does not exist");
        this.ruleRepository.deleteById(id);
    }

    @Override
    public boolean checkIpAccess(Route route) {
        // Check ips in route
        if(!route.checkIpFormat())
            // Check ip format
            throw new IllegalStateException("Incorrect ip format");
        else if(!route.checkIpNumbers())
            // Check ip snippet
            throw new IllegalStateException("An IP can only has numbers between 0 and 255 (inclusive)");

        List<Rule> rulesAllowed = this.ruleRepository.findByAllow(true);
        List<RuleEntity> ruleEntitiesAllowed = rulesAllowed.stream()
                .map(this.ruleToRuleEntityMapper::mapTo)
                .toList();

        return ruleEntitiesAllowed.stream()
                .anyMatch(ruleEntity -> ruleEntity.checkSourceIpAccess(route.getSourceIp())
                && ruleEntity.checkDestinationIpAccess(route.getDestinationIp()));
    }
}
