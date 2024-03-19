package com.sbz.ipfilter.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbz.ipfilter.core.service.IRuleService;
import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.core.mapper.Mapper;
import com.sbz.ipfilter.utils.RuleEntityTestData;
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
    private final Mapper<RuleEntity, RuleDto> ruleEntityToRuleDtoMapper;
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    RuleControllerTest(IRuleService ruleService,
                       Mapper<RuleEntity, RuleDto> ruleEntityToRuleDtoMapper,
                       ObjectMapper objectMapper,
                       MockMvc mockMvc
    ) {
        this.ruleService = ruleService;
        this.ruleEntityToRuleDtoMapper = ruleEntityToRuleDtoMapper;
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
    }

    // Check that [POST]/rules endpoint response with 201 http code
    @Test
    void testThatCreateRuleSuccessfullyReturnsHttp201Created() throws Exception {
        String ruleJson = objectMapper.writeValueAsString(RuleEntityTestData.createTestRuleEntityA());
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
    void testThatCreateRuleSuccessfullyReturnsRuleCreated() throws Exception {
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        String ruleJson = objectMapper.writeValueAsString(ruleEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lowerSourceIp").value(ruleEntity.getLowerSourceIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.upperSourceIp").value(ruleEntity.getUpperSourceIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lowerDestinationIp").value(ruleEntity.getLowerDestinationIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.upperDestinationIp").value(ruleEntity.getUpperDestinationIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.allow").isBoolean()
        );
    }

    // Check that [POST]/rules endpoint response with 400 http code
    @Test
    void testThatCreateRuleReturnsHttp400BadRequest() throws Exception {
        String ruleJson = objectMapper.writeValueAsString(RuleEntityTestData.createTestRuleEntityIncorrectIpFormat());
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
    void testThatListRulesReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
          MockMvcResultMatchers.status().isOk()
        );
    }

    // Check that [DELETE]/rules/{id} endpoint response with 204 http code
    @Test
    void testThatDeleteRuleReturnsHttp204NoContent() throws Exception {
        RuleDto rule = ruleService.save(
                ruleEntityToRuleDtoMapper.mapTo(RuleEntityTestData.createTestRuleEntityA())
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
    void testThatDeleteRuleReturnsHttp404NoFound() throws Exception {
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

//    // Check that [POST]/rules/check endpoint response with 200 http code
//    @Test
//    void testThatCheckIpReturnsHttp200Ok() throws Exception {
//        ruleService.save(
//                ruleEntityToRuleDtoMapper.mapTo(RuleEntityTestData.createTestRuleEntityA())
//        );
//        String routeJson = objectMapper.writeValueAsString(RouteDtoTestData.createRoute());
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/rules/check")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(routeJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.message").isString()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.status").value(true)
//        );
//    }
//
//    // Check that [POST]/rules/check endpoint response with 400 http code
//    @Test
//    void testThatCheckIpReturnsHttp400BadRequest() throws Exception {
//        ruleService.save(
//                ruleEntityToRuleDtoMapper.mapTo(RuleEntityTestData.createTestRuleEntityA())
//        );
//        String routeJson = objectMapper.writeValueAsString(RouteDtoTestData.createRouteInvalidFormatSourceIp());
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/rules/check")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(routeJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isBadRequest()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.message").isString()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.status").value(false)
//        );
//    }
}