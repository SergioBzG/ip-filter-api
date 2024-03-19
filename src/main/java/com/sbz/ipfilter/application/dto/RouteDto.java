package com.sbz.ipfilter.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDto {

    @NotBlank(message = "A source ip is required")
    @Size(max = 15, message = "Max length is 15")
    @Pattern(regexp = "^(?:\\d{1,3}\\.){3}\\d{1,3}$",
            message = "Incorrect ip format (valid format e.g. 123.32.4.212)")
    private String sourceIp;

    @NotBlank(message = "A destination ip is required")
    @Size(max = 15, message = "Max length is 15")
    @Pattern(regexp = "^(?:\\d{1,3}\\.){3}\\d{1,3}$",
            message = "Incorrect ip format (valid format e.g. 123.32.4.212)")
    private String destinationIp;

}
