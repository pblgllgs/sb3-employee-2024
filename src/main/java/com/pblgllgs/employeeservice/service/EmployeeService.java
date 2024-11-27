package com.pblgllgs.employeeservice.service;

import com.pblgllgs.employeeservice.dto.APIResponseDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployee(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto);

    void deleteEmployee(Long employeeId);
}
