package com.sbz.ipfilter.infrastructure.persistence.repository;


import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.utils.RuleTestData;
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
    public RuleRepositoryTests(final RuleRepository underTest) {
        this.underTest = underTest;
    }

    // Create a Rule
    @Test
    public void testThatRuleCanBeCreated() {
        Rule rule = RuleTestData.createTestRuleA();
        Rule savedRule = underTest.save(rule);
        assertThat(savedRule).isEqualTo(rule);
    }

    // Create and Read multiple Rules
    @Test
    public void testThatMultipleRulesCanBeReadAndRecall() {
        Rule ruleA = RuleTestData.createTestRuleA();
        Rule ruleB = RuleTestData.createTestRuleB();
        underTest.save(ruleA);
        underTest.save(ruleB);
        Iterable<Rule> rules = underTest.findAll();
        assertThat(rules)
                .hasSize(2)
                .containsExactly(ruleA, ruleB);
    }

    // Delete a Rule by id
    @Test
    public void testThatRuleCanBeDeleted() {
        Rule ruleA = RuleTestData.createTestRuleA();
        underTest.save(ruleA);
        underTest.deleteById(ruleA.getId());
        Optional<Rule> optionalRule = underTest.findById(ruleA.getId());
        assertThat(optionalRule).isEmpty();
    }

    @Test
    public void testThatGetIfRuleExistsOrNot() {
        Rule ruleA = RuleTestData.createTestRuleA();
        underTest.save(ruleA);
        boolean exists = underTest.existsById(ruleA.getId());
        assertTrue(exists);
    }

    @Test
    public void testThatGetRulesWithAllowEqualsTrue() {
        Rule ruleA = RuleTestData.createTestRuleA();
        Rule ruleB = RuleTestData.createTestRuleB();
        Rule ruleC = RuleTestData.createTestRuleC();
        underTest.save(ruleA);
        underTest.save(ruleB);
        underTest.save(ruleC);

        List<Rule> savedRules = underTest.findByAllow(true);
        assertThat(savedRules)
                .hasSize(2)
                .containsExactly(ruleA, ruleB);
    }

}