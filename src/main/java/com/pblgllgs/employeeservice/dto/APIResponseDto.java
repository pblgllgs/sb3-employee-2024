package com.pblgllgs.employeeservice.dto;
/*
 *
 * @author pblgl
 * Created on 27-11-2024
 *
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "APIResponseDto model information"
)
public class APIResponseDto {
    @Schema(
            description = "EmployeeDto object"
    )
    private EmployeeDto employeeDto;
    @Schema(
            description = "DepartmentDto object"
    )
    private DepartmentDto departmentDto;
    @Schema(
            description = "OrganizationDto object"
    )
    private OrganizationDto organizationDto;
}
