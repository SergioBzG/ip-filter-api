package com.sbz.ipfilter.application.service.impl;

import com.sbz.ipfilter.application.exception.IpFormatException;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.exception.RuleFormatException;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.domain.model.RouteEntity;
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
    RuleServiceImplTest(IRuleService underTest, Mapper<Rule, RuleDto> ruleToRuleDtoMapper) {
        this.underTest = underTest;
        this.ruleToRuleDtoMapper = ruleToRuleDtoMapper;
    }

    // Create Rule
    @Test
    void testThatSaveRuleIsSuccessful() {
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
    void testThatSaveRuleThrowsIpFormatException() {
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleIncorrectIpFormat());
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.save(ruleDto));
        assertEquals("Incorrect ip format (e.g. valid format : 123.32.4.212)", exception.getMessage());
    }

    @Test
    void testThatSaveRuleThrowsIpFormatExceptionByInvalidNumbersRange() {
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleInvalidNumbersRange());
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.save(ruleDto));
        assertEquals("An IP can only has numbers between 0 and 255 (inclusive)", exception.getMessage());
    }


    @Test
    void testThatSaveRuleThrowsRuleFormatException() {
        RuleDto ruleDto = ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleWithInvalidRange());
        Exception exception = assertThrows(RuleFormatException.class, () -> underTest.save(ruleDto));
        assertEquals("Invalid ip range", exception.getMessage());
    }

    // List all rules
    @Test
    void testThatListRulesIsSuccessful() {
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
    void testThatDeleteRuleThrowsDoesNotExistException() {
        long id = 1L;
        Exception exception = assertThrows(RuleDoesNotExistException.class, () -> underTest.delete(id));
        assertEquals("Rule with id " + id + " does not exist", exception.getMessage());
    }

    @Test
    void testThatCheckIpAccessReturnsTrue() {
        RouteEntity routeEntity = RouteTestData.createRoute();
        boolean allow;
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleA())
            );
            allow = underTest.checkIpAccess(routeEntity);
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        assertTrue(allow);
    }
    @Test
    void testThatCheckIpAccessReturnsFalse() {
        RouteEntity routeEntity = RouteTestData.createRoute();
        boolean allow;
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
            allow = underTest.checkIpAccess(routeEntity);
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        assertFalse(allow);
    }

    @Test
    void testThatCheckIpAccessThrowsIpFormatException() {
        RouteEntity routeEntity = RouteTestData.createRouteInvalidFormatSourceIp();
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.checkIpAccess(routeEntity));
        assertEquals("Incorrect ip format (e.g. valid format : 123.32.4.212)" ,exception.getMessage());
    }

    @Test
    void testThatCheckIpAccessThrowsIpFormatExceptionByInvalidNumbersRange() {
        RouteEntity routeEntity = RouteTestData.createRouteInvalidNumbersRange();
        try {
            underTest.save(ruleToRuleDtoMapper.mapTo(
                    RuleTestData.createTestRuleB())
            );
        } catch (IpFormatException | RuleFormatException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(IpFormatException.class, () -> underTest.checkIpAccess(routeEntity));
        assertEquals("An IP can only has numbers between 0 and 255 (inclusive)" ,exception.getMessage());
    }
}