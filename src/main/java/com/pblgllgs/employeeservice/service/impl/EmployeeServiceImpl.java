package com.pblgllgs.employeeservice.service.impl;
/*
 *
 * @author pblgl
 * Created on 21-11-2024
 *
 */

import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.entity.Employee;
import com.pblgllgs.employeeservice.exception.ResourceNotFoundException;
import com.pblgllgs.employeeservice.repository.EmployeeRepository;
import com.pblgllgs.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeOptional.isPresent()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return modelMapper.map(employeeOptional.get(), EmployeeDto.class);
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
            throw new ResourceNotFoundException("Employee not found");
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
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(employeeId);
    }


}
