package com.pblgllgs.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *
 * @author pblgl
 * Created on 21-11-2024
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "EmployeeDto model information "
)
public class EmployeeDto {
    @Schema(
            description = "Employee id"
    )
    private Long id;
    @Schema(
            description = "Employee First name"
    )
    private String firstName;
    @Schema(
            description = "Employee last name"
    )
    private String lastName;
    @Schema(
            description = "Employee email"
    )
    private String email;
    @Schema(
            description = "Employee department code"
    )
    private String departmentCode;
    @Schema(
            description = "Employee organization code"
    )
    private String organizationCode;
}
