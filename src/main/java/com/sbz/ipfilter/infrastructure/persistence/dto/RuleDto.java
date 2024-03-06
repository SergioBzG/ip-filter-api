package com.sbz.ipfilter.infrastructure.persistence.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "a lower source ip is required")
    private String lowerSourceIp;

    @NotBlank(message = "a lower source ip is required")
    private String upperSourceIp;

    @NotBlank(message = "a lower source ip is required")
    private String lowerDestinationIp;

    @NotBlank(message = "a lower source ip is required")
    private String upperDestinationIp;

    @NotNull(message = "this field is required")
    private Boolean allow;
}
