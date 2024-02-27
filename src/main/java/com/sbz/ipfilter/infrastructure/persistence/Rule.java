package com.sbz.ipfilter.infrastructure.persistence;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name = "Rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "a lower source ip is required")
    @Size(max = 15)
    private String lowerSourceIp;

    @NotBlank(message = "a upper source ip is required")
    @Size(max = 15)
    private String upperSourceIp;

    @NotBlank(message = "a lower destination ip is required")
    @Size(max = 15)
    private String lowerDestinationIp;

    @NotBlank(message = "a upper destination ip is required")
    @Size(max = 15)
    private String upperDestinationIp;

    @NotNull(message = "this field is required")
    private Boolean allow;

}
