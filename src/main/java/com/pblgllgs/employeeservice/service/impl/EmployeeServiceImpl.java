package com.pblgllgs.employeeservice.service.impl;
/*
 *
 * @author pblgl
 * Created on 21-11-2024
 *
 */

import com.pblgllgs.employeeservice.clients.DepartmentClient;
import com.pblgllgs.employeeservice.dto.APIResponseDto;
import com.pblgllgs.employeeservice.dto.DepartmentDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.entity.Employee;
import com.pblgllgs.employeeservice.enums.Constants;
import com.pblgllgs.employeeservice.exception.ResourceNotFoundException;
import com.pblgllgs.employeeservice.repository.EmployeeRepository;
import com.pblgllgs.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final DepartmentClient departmentClient;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeOptional.isPresent()) {
            throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND.getValue());
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public APIResponseDto getEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND.getValue());
        }
        ResponseEntity<DepartmentDto> department = departmentClient.getDepartment(employeeOptional.get().getDepartmentCode());
        return new APIResponseDto(
                modelMapper.map(employeeOptional.get(), EmployeeDto.class),
                department.getBody()
        );
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .toList();
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND.getValue());
        }
        Employee employeeToUpdate = employeeOptional.get();
        employeeToUpdate.setEmail(employeeDto.getEmail());
        employeeToUpdate.setFirstName(employeeDto.getFirstName());
        employeeToUpdate.setLastName(employeeDto.getLastName());
        Employee savedEmployee = employeeRepository.save(employeeToUpdate);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND.getValue());
        }
        employeeRepository.deleteById(employeeId);
    }


}
