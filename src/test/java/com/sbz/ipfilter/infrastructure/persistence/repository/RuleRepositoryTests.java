package com.sbz.ipfilter.infrastructure.persistence.repository;


import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.utils.RuleEntityTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RuleRepositoryTests {
    private final RuleRepository underTest;

    @Autowired
    RuleRepositoryTests(final RuleRepository underTest) {
        this.underTest = underTest;
    }

    // Create a Rule
    @Test
    void testThatRuleCanBeCreated() {
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        RuleEntity savedRuleEntity = underTest.save(ruleEntity);
        assertThat(savedRuleEntity).isEqualTo(ruleEntity);
    }

    // Create and Read multiple Rules
    @Test
    void testThatMultipleRulesCanBeCreatedAndRecall() {
        RuleEntity ruleEntityA = RuleEntityTestData.createTestRuleEntityA();
        RuleEntity ruleEntityB = RuleEntityTestData.createTestRuleEntityB();
        underTest.save(ruleEntityA);
        underTest.save(ruleEntityB);
        Iterable<RuleEntity> rules = underTest.findAll();
        assertThat(rules)
                .hasSize(2)
                .containsExactly(ruleEntityA, ruleEntityB);
    }

    // Delete a Rule by id
    @Test
    void testThatRuleCanBeDeleted() {
        RuleEntity ruleEntityA = RuleEntityTestData.createTestRuleEntityA();
        underTest.save(ruleEntityA);
        underTest.deleteById(ruleEntityA.getId());
        Optional<RuleEntity> optionalRule = underTest.findById(ruleEntityA.getId());
        assertThat(optionalRule).isEmpty();
    }

    @Test
    void testThatGetIfRuleExistsOrNot() {
        RuleEntity ruleEntityA = RuleEntityTestData.createTestRuleEntityA();
        underTest.save(ruleEntityA);
        boolean exists = underTest.existsById(ruleEntityA.getId());
        assertTrue(exists);
    }

    @Test
    void testThatGetRulesWithAllowEqualsTrue() {
        RuleEntity ruleEntityA = RuleEntityTestData.createTestRuleEntityA();
        RuleEntity ruleEntityB = RuleEntityTestData.createTestRuleEntityB();
        RuleEntity ruleEntityC = RuleEntityTestData.createTestRuleEntityC();
        underTest.save(ruleEntityA);
        underTest.save(ruleEntityB);
        underTest.save(ruleEntityC);

        List<RuleEntity> savedRuleEntities = underTest.findByAllow(true);
        assertThat(savedRuleEntities)
                .hasSize(2)
                .containsExactly(ruleEntityA, ruleEntityB);
    }

}