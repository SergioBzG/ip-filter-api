package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.exceptions.IpFormatException;
import com.sbz.ipfilter.application.exceptions.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exceptions.RuleFormatException;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.Route;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import com.sbz.ipfilter.utils.RouteTestData;
import com.sbz.ipfilter.utils.RuleTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RuleServiceImplTest {

    private final IRuleService underTest;

    private final Mapper<Rule, RuleDto> ruleToRuleDtoMapper;

    @Autowired
    public RuleServiceImplTest(IRuleService underTest, Mapper<Rule, RuleDto> ruleToRuleDtoMapper) {
        this.underTest = underTest;
        this.ruleToRuleDtoMapper = ruleToRuleDtoMapper;
    }

    // Create Rule
    @Test
    public void testThatSaveRuleIsSuccessful() {
        Rule rule = RuleTestData.createTestRuleA();
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(rule);
        RuleDto savedRule;
        try {
            savedRule = underTest.save(ruleDto);
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        assertThat(savedRule).isEqualTo(ruleDto);
    }

    @Test
    public void testThatSaveRuleThrowsIpFormatException() {
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleIncorrectIpFormat());
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.save(ruleDto));
        assertEquals("Incorrect ip format", exception.getMessage());
    }

    @Test
    public void testThatSaveRuleThrowsIpFormatExceptionByInvalidNumbersRange() {
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleInvalidNumbersRange());
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.save(ruleDto));
        assertEquals("An IP can only has numbers between 0 and 255 (inclusive)", exception.getMessage());
    }


    @Test
    public void testThatSaveRuleThrowsRuleFormatException() {
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleWithInvalidRange());
        Exception exception = assertThrows(RuleFormatException.class, () -> underTest.save(ruleDto));
        assertEquals("Invalid ip range", exception.getMessage());
    }

    // List all rules
    @Test
    public void testThatListRulesIsSuccessful() {
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleA())
            );
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        Page<RuleDto> rules = underTest.findAll(Pageable.ofSize(20));
        assertThat(rules)
                .hasSize(2);
    }

    // Delete rule by id
    @Test
    public void testThatDeleteRuleThrowsDoesNotExistException() {
        long id = 1L;
        Exception exception = assertThrows(RuleDoesNotExistException.class, () -> underTest.delete(id));
        assertEquals("Rule with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    public void testThatCheckIpAccessReturnsTrue() {
        Route route = RouteTestData.createRoute();
        boolean allow;
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleA())
            );
            allow = underTest.checkIpAccess(route);
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        assertTrue(allow);
    }
    @Test
    public void testThatCheckIpAccessReturnsFalse() {
        Route route = RouteTestData.createRoute();
        boolean allow;
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
            allow = underTest.checkIpAccess(route);
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        assertFalse(allow);
    }

    @Test
    public void testThatCheckIpAccessThrowsIpFormatException() {
        Route route = RouteTestData.createRouteInvalidFormat();
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.checkIpAccess(route));
        assertEquals("Incorrect ip format" ,exception.getMessage());
    }

    @Test
    public void testThatCheckIpAccessThrowsIpFormatExceptionByInvalidNumbersRange() {
        Route route = RouteTestData.createRouteInvalidNumbersRange();
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.checkIpAccess(route));
        assertEquals("An IP can only has numbers between 0 and 255 (inclusive)" ,exception.getMessage());
    }
}