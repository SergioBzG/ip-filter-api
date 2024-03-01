package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.application.utils.Route;
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
    private final Mapper<RuleDto, RuleEntity> ruleEntityMapper;
    private final Mapper<Rule, RuleDto> ruleDtoMapper;


    public RuleServiceImpl(
            final RuleRepository ruleRepository,
            final Mapper<Rule, RuleDto> ruleDtoMapper,
            final Mapper<RuleDto, RuleEntity> ruleEntityMapper
    ) {
        this.ruleRepository = ruleRepository;
        this.ruleEntityMapper = ruleEntityMapper;
        this.ruleDtoMapper = ruleDtoMapper;
    }

    @Override
    public RuleDto save(RuleDto ruleDto) {
        // Get RuleEntity of domain to validations
        RuleEntity ruleEntity = ruleEntityMapper.mapTo(ruleDto);
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
                this.ruleDtoMapper.mapFrom(ruleDto)
        );
        return this.ruleDtoMapper.mapTo(ruleSaved);
    }

    @Override
    public List<RuleDto> findAll() {
        Iterable<Rule> rulesIterable = this.ruleRepository.findAll();
        return StreamSupport.stream(rulesIterable.spliterator(), false)
                .map(this.ruleDtoMapper::mapTo)
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
        // TODO : retrieve all rules with allow = true and check against them the access
        return false;
    }
}
