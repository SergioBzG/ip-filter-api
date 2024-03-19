package com.sbz.ipfilter.core.mapper.impl;

import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.utils.RuleDtoTestData;
import com.sbz.ipfilter.utils.RuleEntityTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleEntityToRuleDtoMapperTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private RuleEntityToRuleDtoMapper underTest;

    @Test
    void testThatMapToReturnsARuleDto() {
        // Data for test
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoA();
        when(modelMapper.map(ruleEntity, RuleDto.class))
                .thenReturn(ruleDto);
        // Map to ruleDto
        RuleDto result = underTest.mapTo(ruleEntity);
        // Asserts
        assertNotNull(result);
        assertEquals(ruleDto, result);
    }

    @Test
    void testThatMapFromReturnsARuleEntity() {
        // Data for test
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoA();
        when(modelMapper.map(ruleDto, RuleEntity.class))
                .thenReturn(ruleEntity);
        // Map from ruleEntity
        RuleEntity result = underTest.mapFrom(ruleDto);
        // Asserts
        assertNotNull(result);
        assertEquals(ruleEntity, result);
    }
}