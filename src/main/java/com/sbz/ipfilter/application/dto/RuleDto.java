package com.sbz.ipfilter.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "A lower source ip is required")
    @Size(max = 15, message = "Max length is 15")
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Incorrect ip format (valid format e.g. 123.32.4.212)")
    private String lowerSourceIp;

    @NotBlank(message = "A lower source ip is required")
    @Size(max = 15, message = "Max length is 15")
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Incorrect ip format (valid format e.g. 123.32.4.212)")
    private String upperSourceIp;

    @NotBlank(message = "A lower source ip is required")
    @Size(max = 15, message = "Max length is 15")
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Incorrect ip format (valid format e.g. 123.32.4.212)")
    private String lowerDestinationIp;

    @NotBlank(message = "A lower source ip is required")
    @Size(max = 15, message = "Max length is 15")
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Incorrect ip format (valid format e.g. 123.32.4.212)")
    private String upperDestinationIp;

    @NotNull(message = "allow field is required")
    private Boolean allow;
}
