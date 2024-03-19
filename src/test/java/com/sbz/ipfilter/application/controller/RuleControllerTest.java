package com.sbz.ipfilter.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.application.exception.InvalidIpRangeException;
import com.sbz.ipfilter.application.exception.InvalidOrMissingDataException;
import com.sbz.ipfilter.application.validator.impl.RouteValidator;
import com.sbz.ipfilter.application.validator.impl.RuleValidator;
import com.sbz.ipfilter.core.service.IRuleService;
import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.utils.RouteDtoTestData;
import com.sbz.ipfilter.utils.RuleDtoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RuleControllerTest {
    @InjectMocks
    private RuleController underTest;
    @Mock
    private IRuleService ruleService;
    @Mock
    private RuleValidator ruleValidator;
    @Mock
    private RouteValidator routeValidator;
    @Mock
    private BindingResult errors;
    private MockMvc mockMvc;

    @Captor
    ArgumentCaptor<RuleDto> ruleDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<RouteDto> routeDtoArgumentCaptor;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }


    // Check that [POST]/rules endpoint throws InvalidOrMissingDataException
    @Test
    void testThatCreateRulesThrowsInvalidOrMissingDataException() {
        // Data for test
        doThrow(new InvalidOrMissingDataException(
                "A lower source ip is required"
                )
        ).when(ruleValidator)
                .checkInvalidOrMissingData(any(BindingResult.class));
        //Throw exception
        Exception exception =  assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.createRule(
                        RuleDtoTestData.createTestRuleDtoA(),
                        errors
                )
        );
        // Asserts
        assertEquals("A lower source ip is required", exception.getMessage());
        verify(ruleValidator, times(0)).checkIpNumbersInRule(any(RuleDto.class));
        verifyNoInteractions(ruleService);
    }

    // Check that [POST]/rules endpoint throws InvalidOrMissingDataException by check ip number in rule
    @Test
    void testThatCreateRulesThrowsInvalidOrMissingDataExceptionByCheckIpNumberInRule() {
        // Data for test
        RuleDto ruleDtoToCreate = RuleDtoTestData.createTestRuleDtoA();
        doThrow(new InvalidOrMissingDataException(
                "An IP can only has numbers between 0 and 255 (inclusive)"
                )
        ).when(ruleValidator)
                .checkIpNumbersInRule(ruleDtoArgumentCaptor.capture());
        //Throw exception
        Exception exception =  assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.createRule(
                        ruleDtoToCreate,
                        errors
                )
        );
        // Asserts
        assertEquals(
                "An IP can only has numbers between 0 and 255 (inclusive)",
                exception.getMessage()
        );
        assertEquals(ruleDtoToCreate, ruleDtoArgumentCaptor.getValue());
        verify(ruleValidator, times(1)).checkInvalidOrMissingData(any(BindingResult.class));
        verify(ruleValidator, times(0)).checkSourceAndDestinationIpRange(any(RuleDto.class));
        verifyNoInteractions(ruleService);
    }


    // Check that [POST]/rules endpoint throws InvalidIpRangeException
    @Test
    void testThatCreateRulesThrowsInvalidIpRangeException() {
        // Data for test
        RuleDto ruleDtoToCreate = RuleDtoTestData.createTestRuleDtoA();
        doThrow(new InvalidIpRangeException(
                "Invalid IP range"
                )
        ).when(ruleValidator)
                .checkIpNumbersInRule(ruleDtoArgumentCaptor.capture());
        //Throw exception
        Exception exception =  assertThrows(
                InvalidIpRangeException.class,
                () -> underTest.createRule(
                        ruleDtoToCreate,
                        errors
                )
        );
        // Asserts
        assertEquals(
                "Invalid IP range",
                exception.getMessage()
        );
        assertEquals(ruleDtoToCreate, ruleDtoArgumentCaptor.getValue());
        verify(ruleValidator, times(1)).checkInvalidOrMissingData(any(BindingResult.class));
        verify(ruleValidator, times(1)).checkIpNumbersInRule(any(RuleDto.class));
        verifyNoInteractions(ruleService);
    }

    // Check that [POST]/rules endpoint response with 201 http code and rule created
    @Test
    void testThatCreateRuleSuccessfullyReturnsHttp201CreatedAndRuleCreated() throws Exception {
        // Data for test
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoA();
        String ruleJson = this.asJsonString(ruleDto);
        when(ruleService.save(any(RuleDto.class)))
                .thenReturn(ruleDto);
        // Create rule and verify results
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ruleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.upperSourceIp").value(ruleDto.getUpperSourceIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lowerDestinationIp").value(ruleDto.getLowerDestinationIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.upperDestinationIp").value(ruleDto.getUpperDestinationIp())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.allow").isBoolean()
        );
    }

    // Check that [DELETE]/rules/{id} endpoint response with 204 http code
    @Test
    void testThatDeleteRuleReturnsHttp204NoContent() {
        // Data for test
        Long id = 3L;
        doNothing().when(ruleService).delete(longArgumentCaptor.capture());
        // Delete rule
        ResponseEntity<?> response = underTest.deleteRule(id);
        // Asserts
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(3L, longArgumentCaptor.getValue());
    }

    // Check that [POST]/rules/check endpoint throws InvalidOrMissingDataException
    @Test
    void testThatCheckIpThrowsInvalidOrMissingDataException() {
        // Data for test
        doThrow(new InvalidOrMissingDataException(
                "A source ip is required"
                )
        ).when(routeValidator)
                .checkInvalidOrMissingData(any(BindingResult.class));
        // Throw exception
        Exception exception = assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.checkIp(RouteDtoTestData.createRouteDtoA(), errors)
        );
        // Asserts
        assertEquals("A source ip is required", exception.getMessage());
        verify(routeValidator, times(0)).checkIpNumbersInRoute(any(RouteDto.class));
        verifyNoInteractions(ruleService);
    }

    // Check that [POST]/rules/check endpoint throws InvalidOrMissingDataException by check ip numbers in route
    @Test
    void testThatCheckIpThrowsInvalidOrMissingDataExceptionByCheckIpNumbersInRoute() {
        // Data for test
        doThrow(new InvalidOrMissingDataException(
                "An IP can only has numbers between 0 and 255 (inclusive)"
                )
        ).when(routeValidator)
                .checkIpNumbersInRoute(any(RouteDto.class));
        // Throw exception
        Exception exception = assertThrows(
                InvalidOrMissingDataException.class,
                () -> underTest.checkIp(RouteDtoTestData.createRouteDtoA(), errors)
        );
        // Asserts
        assertEquals(
                "An IP can only has numbers between 0 and 255 (inclusive)",
                exception.getMessage()
        );
        verify(routeValidator, times(1)).checkInvalidOrMissingData(any(BindingResult.class));
        verifyNoInteractions(ruleService);
    }

    // Check that [POST]/rules/check endpoint response with 200 http code and allowing access
    @Test
    void testThatCheckIpReturnsHttp200OkAllowingAccess() throws Exception {
        // Data for test
        RouteDto routeDto = RouteDtoTestData.createRouteDtoA();
        String routeJson = this.asJsonString(routeDto);
        when(ruleService.checkIpAccess(
                any(RouteDto.class))
        ).thenReturn(true);
        // Check ip access and verify results
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(routeJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Allowed access")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(true)
        );
    }

    // Check that [POST]/rules/check endpoint response with 200 http code and denying access
    @Test
    void testThatCheckIpReturnsHttp200OkDenyingAccess() throws Exception {
        // Data for test
        RouteDto routeDto = RouteDtoTestData.createRouteDtoA();
        String routeJson = this.asJsonString(routeDto);
        when(ruleService.checkIpAccess(
                routeDtoArgumentCaptor.capture())
        ).thenReturn(false);
        // Check ip access and verify results
        mockMvc.perform(
                MockMvcRequestBuilders.post("/rules/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(routeJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Denied access")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(true)
        );
    }

    // Check that [GET]/rules endpoint response with a 200 http code and a page of rules
    @Test
    void testThatGetRulesReturnsHttp200OkAndPageOfRules() {
        // Data for test
        Page<RuleDto> rulesDtoPage = new PageImpl<>(
                List.of(
                        RuleDtoTestData.createTestRuleDtoA(),
                        RuleDtoTestData.createTestRuleDtoB()
                )
        );
        when(ruleService.findAll(any(Pageable.class))).
                thenReturn(rulesDtoPage);
        // Get rules
        ResponseEntity<Page<RuleDto>> response = underTest.getRules(Pageable.unpaged());
        // Asserts
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rulesDtoPage, response.getBody());
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}