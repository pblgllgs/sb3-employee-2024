package com.pblgllgs.employeeservice.clients;

import com.pblgllgs.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentClient {
    @GetMapping("/api/departments/{department-code}")
    ResponseEntity<DepartmentDto> getDepartment(@PathVariable("department-code") String departmentCode);
}
