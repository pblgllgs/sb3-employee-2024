package com.pblgllgs.employeeservice.controller;
/*
 *
 * @author pblgl
 * Created on 21-11-2024
 *
 */

import com.pblgllgs.employeeservice.dto.APIResponseDto;
import com.pblgllgs.employeeservice.dto.EmployeeDto;
import com.pblgllgs.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeCreated = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employeeCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<APIResponseDto> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody EmployeeDto employeeDto
    ) {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employeeDto), HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
    }
}
