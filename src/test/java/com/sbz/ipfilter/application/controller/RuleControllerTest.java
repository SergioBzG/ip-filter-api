package com.sbz.ipfilter.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbz.ipfilter.application.service.IRuleService;
import com.sbz.ipfilter.infrastructure.persistence.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.Rule;
import com.sbz.ipfilter.infrastructure.persistence.mapper.Mapper;
import com.sbz.ipfilter.utils.RouteTestData;
import com.sbz.ipfilter.utils.RuleTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class RuleControllerTest {

    private final IRuleService ruleService;
    private final Mapper<Rule, RuleDto> ruleToRuleDtoMapper;
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    public RuleControllerTest(IRuleService ruleService,
                              Mapper<Rule, RuleDto> ruleToRuleDtoMapper,
                              ObjectMapper objectMapper,
                              MockMvc mockMvc
    ) {
        this.ruleService = ruleService;
        this.ruleToRuleDtoMapper = ruleToRuleDtoMapper;
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    // Check that [POST]/rules endpoint response with 201 http code
    @Test
    public void testThatCreateRuleSuccessfullyReturnsHttp201Created() throws Exception {
        String ruleJson = objectMapper.writeValueAsString(RuleTestData.createTestRuleA());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    // Check that [POST]/rules endpoint response with the rule created
    @Test
    public void testThatCreateRuleSuccessfullyReturnsRuleCreated() throws Exception {
        Rule rule = RuleTestData.createTestRuleA();
        String ruleJson = objectMapper.writeValueAsString(rule);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lowerSourceIp").value(rule.getLowerSourceIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.upperSourceIp").value(rule.getUpperSourceIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lowerDestinationIp").value(rule.getLowerDestinationIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.upperDestinationIp").value(rule.getUpperDestinationIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.allow").isBoolean()
        );
    }

    // Check that [POST]/rules endpoint response with 400 http code
    @Test
    public void testThatCreateRuleReturnsHttp400BadRequest() throws Exception {
        String ruleJson = objectMapper.writeValueAsString(RuleTestData.createTestRuleIncorrectIpFormat());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").isBoolean()
        );
    }

    // Check that [GET]/rules endpoint response with 200 http code
    @Test
    public void testThatListRulesReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.status().isOk()
        );
    }

    // Check that [DELETE]/rules/{id} endpoint response with 204 http code
    @Test
    public void testThatDeleteRuleReturnsHttp204NoContent() throws Exception {
        RuleDto rule = ruleService.save(
                ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleA())
        );
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/rules/" + rule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    // Check that [DELETE]/rules/{id} endpoint response with 404 http code
    @Test
    public void testThatDeleteRuleReturnsHttp404NoFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/rules/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").isBoolean()
        );
    }

    // Check that [POST]/rules/check endpoint response with 200 http code
    @Test
    void testThatCheckIpReturnsHttp200Ok() throws Exception {
        ruleService.save(
                ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleA())
        );
        String routeJson = objectMapper.writeValueAsString(RouteTestData.createRoute());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(routeJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(true)
        );
    }

    // Check that [POST]/rules/check endpoint response with 400 http code
    @Test
    void testThatCheckIpReturnsHttp400BadRequest() throws Exception {
        ruleService.save(
                ruleToRuleDtoMapper.mapTo(RuleTestData.createTestRuleA())
        );
        String routeJson = objectMapper.writeValueAsString(RouteTestData.createRouteInvalidFormat());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(routeJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(false)
        );
    }
}