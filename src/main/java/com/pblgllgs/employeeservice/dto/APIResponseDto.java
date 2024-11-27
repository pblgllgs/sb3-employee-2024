package com.pblgllgs.employeeservice.dto;
/*
 *
 * @author pblgl
 * Created on 27-11-2024
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponseDto {
    private EmployeeDto employeeDto;
    private DepartmentDto departmentDto;
}
