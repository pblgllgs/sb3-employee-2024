package com.pblgllgs.employeeservice.service.impl;
/*
 *
 * @author pblgl
 * Created on 21-11-2024
 *
 */

import com.pblgllgs.employeeservice.clients.DepartmentClient;
import com.pblgllgs.employeeservice.clients.OrganizationClient;
import com.pblgllgs.employeeservice.dto.APIResponseDto;
import com.pblgllgs.employeeservice.dto.DepartmentDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.dto.OrganizationDto;
import com.pblgllgs.employeeservice.entity.Employee;
import com.pblgllgs.employeeservice.enums.Constants;
import com.pblgllgs.employeeservice.exception.ResourceNotFoundException;
import com.pblgllgs.employeeservice.repository.EmployeeRepository;
import com.pblgllgs.employeeservice.service.EmployeeService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final DepartmentClient departmentClient;
    private final OrganizationClient organizationClient;

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

    //    @CircuitBreaker(
//            name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment"
//    )
    @Retry(
            name = "${spring.application.name}",
            fallbackMethod = "getDefaultDepartment"
    )
    @Override
    public APIResponseDto getEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND.getValue());
        }
        log.info("Department {}", employeeOptional.get().getDepartmentCode());
        log.info("Organization: {}", employeeOptional.get().getOrganizationCode());

        ResponseEntity<OrganizationDto> organization = organizationClient.getOrganizationByCode(employeeOptional.get().getOrganizationCode());
        log.info("organization client - Get employee with id: {}, organization object {}", employeeId, organization.getBody());
        ResponseEntity<DepartmentDto> department = departmentClient.getDepartment(employeeOptional.get().getDepartmentCode());
        log.info("department client - Get employee with id: {}, department object {}", employeeId, department.getBody());
        return new APIResponseDto(
                modelMapper.map(employeeOptional.get(), EmployeeDto.class),
                department.getBody(),
                organization.getBody()

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
        employeeToUpdate.setDepartmentCode(employeeDto.getDepartmentCode());
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

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception e) {
        log.info("getDefaultDepartment - Get employee with id: {}", employeeId);
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND.getValue());
        }
        DepartmentDto departmentDtoDefault = new DepartmentDto(
                123L,
                "dev",
                "DEV-001",
                "dev-department"
        );

        OrganizationDto organizationDtoDefault = new OrganizationDto(
                123L,
                "org",
                "description",
                "QAZ-123",
                LocalDateTime.now()
        );
        return new APIResponseDto(
                modelMapper.map(employeeOptional.get(), EmployeeDto.class),
                departmentDtoDefault,
                organizationDtoDefault
        );
    }
}