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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(
        name = "Employee controller",
        description = "Employee controller exposes REST APIs for employee service"
)
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Save employee REST ENDPOINT",
            description = "Save employee REST API is used to save employee object in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus CREATED"
    )
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeCreated = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employeeCreated, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get employee by id REST ENDPOINT",
            description = "Get employee by id REST API is used to get employee object by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus OK"
    )
    @GetMapping("/{employeeId}")
    public ResponseEntity<APIResponseDto> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all employees REST ENDPOINT",
            description = "Get all employees REST API is used to get all employees in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus OK"
    )
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update employee by id REST ENDPOINT",
            description = "Update employee by id REST API is used to update department object by id and save in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus OK"
    )
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody EmployeeDto employeeDto
    ) {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employeeDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete employee by id REST ENDPOINT",
            description = "Delete employee by id REST API is used to delete department object by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus OK"
    )
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
    }
}
