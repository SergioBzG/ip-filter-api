package com.sbz.ipfilter.core.service.impl;

import com.sbz.ipfilter.application.dto.RouteDto;
import com.sbz.ipfilter.core.mapper.impl.RuleEntityToRuleDtoMapper;
import com.sbz.ipfilter.core.mapper.impl.RuleEntityToRuleMapper;
import com.sbz.ipfilter.application.exception.RuleDoesNotExistException;
import com.sbz.ipfilter.application.dto.RuleDto;
import com.sbz.ipfilter.infrastructure.persistence.entity.RuleEntity;
import com.sbz.ipfilter.infrastructure.persistence.repository.RuleRepository;
import com.sbz.ipfilter.utils.RouteDtoTestData;
import com.sbz.ipfilter.utils.RuleDtoTestData;
import com.sbz.ipfilter.utils.RuleEntityTestData;
import com.sbz.ipfilter.utils.RuleTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RuleServiceImplTest {
//    @InjectMocks
    private RuleServiceImpl underTest;
    @Mock
    private RuleRepository ruleRepository;
    @Mock
    private RuleEntityToRuleDtoMapper ruleEntityToRuleDtoMapper;
    @Mock
    private RuleEntityToRuleMapper ruleEntityToRuleMapper;

    @Captor
    ArgumentCaptor<RuleEntity> ruleEntityArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new RuleServiceImpl(ruleRepository, ruleEntityToRuleDtoMapper, ruleEntityToRuleMapper);
    }

    // Create Rule
    @Test
    void testThatSaveRuleIsSuccessful() {
        // Data for test
        RuleDto ruleDto = RuleDtoTestData.createTestRuleDtoA();
        RuleEntity ruleEntity = RuleEntityTestData.createTestRuleEntityA();
        when(ruleRepository.save(
                ruleEntityArgumentCaptor.capture())
        ).thenReturn(ruleEntity);
        when(ruleEntityToRuleDtoMapper.mapTo(any(RuleEntity.class))).thenReturn(ruleDto);
        when(ruleEntityToRuleDtoMapper.mapFrom(any(RuleDto.class))).thenReturn(ruleEntity);
        // Save rule
        RuleDto ruleSaved = underTest.save(ruleDto);
        // Asserts
        assertNotNull(ruleSaved);
        assertEquals(ruleDto, ruleSaved);
        assertEquals(ruleEntity, ruleEntityArgumentCaptor.getValue());
        verify(ruleRepository, times(1)).save(any(RuleEntity.class));
    }


    // List all rules
    @Test
    void testThatListRulesIsSuccessful() {
        // Data for test
        Page<RuleEntity> ruleEntityPage = new PageImpl<>(
                List.of(
                        RuleEntityTestData.createTestRuleEntityA(),
                        RuleEntityTestData.createTestRuleEntityB()
                ),
                Pageable.unpaged(),
                2
        );
        when(ruleRepository.findAll(any(Pageable.class)))
                .thenReturn(ruleEntityPage);
        // List rules
        Page<RuleDto> rulesPage = underTest.findAll(Pageable.unpaged());
        // Asserts
        assertNotNull(rulesPage);
        assertThat(rulesPage).hasSize(2);
    }

    // Delete rule by id
    @Test
    void testThatDeleteRuleThrowsDoesNotExistException() {
        // Data for test
        long id = 1L;
        when(ruleRepository.existsById(
                longArgumentCaptor.capture())
        ).thenReturn(false);
        // Throw exception
        Exception exception = assertThrows(
                RuleDoesNotExistException.class,
                () -> underTest.delete(id)
        );
        // Asserts
        assertEquals(
                "Rule with id " + id + " does not exist",
                exception.getMessage()
        );
        assertEquals(id, longArgumentCaptor.getValue());
        verify(ruleRepository, times(0)).deleteById(any(Long.class));
        verify(ruleRepository, times(1)).existsById(any(Long.class));
    }

    @Test
    void testThatDeleteRuleIsSuccessful() {
        // Data for test
        long id = 1L;
        when(ruleRepository.existsById(
                longArgumentCaptor.capture())
        ).thenReturn(true);
        // Delete rule
        underTest.delete(id);
        // Asserts
        assertEquals(id, longArgumentCaptor.getValue());
        verify(ruleRepository, times(1)).deleteById(any(Long.class));
        verify(ruleRepository, times(1)).existsById(any(Long.class));
    }

    @Test
    void testThatCheckIpAccessReturnsTrue() {
        // Data for test
        RouteDto routeDto = RouteDtoTestData.createRouteDtoA();
        List<RuleEntity> ruleEntities = List.of(
                RuleEntityTestData.createTestRuleEntityA()
        );
        when(ruleRepository.findByAllow(anyBoolean()))
                .thenReturn(ruleEntities);
        when(ruleEntityToRuleMapper.mapTo(any(RuleEntity.class)))
                .thenReturn(RuleTestData.createTestRuleA());
        // Check ip access
        boolean allow = underTest.checkIpAccess(routeDto);
        assertTrue(allow);
        verify(ruleRepository, times(1)).findByAllow(anyBoolean());
    }
    @Test
    void testThatCheckIpAccessReturnsFalse() {
        // Data for test
        RouteDto routeDto = RouteDtoTestData.createRouteDtoB();
        List<RuleEntity> ruleEntities = List.of(
                RuleEntityTestData.createTestRuleEntityA()
        );
        when(ruleRepository.findByAllow(anyBoolean()))
                .thenReturn(ruleEntities);
        when(ruleEntityToRuleMapper.mapTo(any(RuleEntity.class)))
                .thenReturn(RuleTestData.createTestRuleA());
        // Check ip access
        boolean allow = underTest.checkIpAccess(routeDto);
        assertFalse(allow);
        verify(ruleRepository, times(1)).findByAllow(anyBoolean());
    }

}