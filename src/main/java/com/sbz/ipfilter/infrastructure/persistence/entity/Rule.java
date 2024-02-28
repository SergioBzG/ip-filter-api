package com.sbz.ipfilter.infrastructure.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "a lower source ip is required")
    @Size(max = 15)
    private String lowerSourceIp;

    @NotNull
    @NotBlank(message = "a upper source ip is required")
    @Size(max = 15)
    private String upperSourceIp;

    @NotNull
    @NotBlank(message = "a lower destination ip is required")
    @Size(max = 15)
    private String lowerDestinationIp;

    @NotNull
    @NotBlank(message = "a upper destination ip is required")
    @Size(max = 15)
    private String upperDestinationIp;

    @NotNull(message = "this field is required")
    private Boolean allow;

}
