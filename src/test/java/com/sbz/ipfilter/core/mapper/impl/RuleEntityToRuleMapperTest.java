package com.sbz.ipfilter.core.mapper.impl;

import com.sbz.ipfilter.core.domain.model.Rule;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.utils.RuleEntityTestData;
import com.sbz.ipfilter.utils.RuleTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleEntityToRuleMapperTest {
    private RuleEntityToRuleMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new RuleEntityToRuleMapper();
    }

    @Test
    void testThatMapToReturnsARule() {
        // Data for test
        Rule rule = RuleTestData.createTestRuleA();
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        // Map to rule
        Rule result = underTest.mapTo(ruleEntity);
        // Asserts
        assertNotNull(result);
        assertEquals(rule, result);
    }

    @Test
    void testThatMapFromReturnsARuleEntity() {
        // Data for test
        Rule rule = RuleTestData.createTestRuleA();
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        ruleEntity.setId(null);
        // Map to rule
        RuleEntity result = underTest.mapFrom(rule);
        // Asserts
        assertNotNull(result);
        assertEquals(ruleEntity, result);
    }
}