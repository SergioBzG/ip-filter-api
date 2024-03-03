package com.sbz.ipfilter.infrastructure.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleDto implements Serializable {
    private Long id;
    private String lowerSourceIp;
    private String upperSourceIp;
    private String lowerDestinationIp;
    private String upperDestinationIp;
    private Boolean allow;
}
