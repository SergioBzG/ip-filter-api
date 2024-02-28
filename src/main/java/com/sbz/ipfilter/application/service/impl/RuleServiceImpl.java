package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.service.IRuleService;
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
    private final Mapper<Rule, RuleEntity> ruleEntityMapper;
    private final Mapper<Rule, RuleDto> ruleDtoMapper;


    public RuleServiceImpl(
            final RuleRepository ruleRepository,
            final Mapper<Rule, RuleDto> ruleDtoMapper,
            final Mapper<Rule, RuleEntity> ruleEntityMapper
    ) {
        this.ruleRepository = ruleRepository;
        this.ruleEntityMapper = ruleEntityMapper;
        this.ruleDtoMapper = ruleDtoMapper;
    }

    @Override
    public RuleDto save(RuleDto ruleDto) {
        // TODO: Verify rule
        Rule rule = this.ruleDtoMapper.mapFrom(ruleDto);
        Rule ruleSaved = this.ruleRepository.save(
                rule
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
}
