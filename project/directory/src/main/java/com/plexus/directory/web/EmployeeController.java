package com.plexus.directory.web;

import com.plexus.directory.domain.dto.EmployeeDto;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import com.plexus.directory.facade.impl.EmployeeFacadeImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeFacadeImpl facade;

    public EmployeeController(EmployeeFacadeImpl facade) {
        this.facade = facade;
    }

    @GetMapping({"/{page}/{size}", "/"})
    public ResponseEntity<EmployeePageResponse> getEmployees(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) Integer size) {
        //los page y size por defecto
        int resolvedPage = (page != null) ? page - 1 : 0;
        int resolvedSize = (size != null) ? size : 10;

        return facade.getEmployeesPaged(resolvedPage, resolvedSize);
    }

    @GetMapping("/id/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int employeeId) {
        return facade.getEmployeeById(employeeId);
    }

    @GetMapping("/name/{employeeName}")
    public ResponseEntity<EmployeePageResponse> getEmployeesByName(@PathVariable String employeeName) {
        return facade.getEmployeesByName(employeeName);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employee) {
        return facade.createEmployee(employee);
    }

    @PutMapping
    public ResponseEntity<String> updateEmployee(@Valid @RequestBody EmployeeDto employee) {
        return facade.updateEmployee(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId) {
        return facade.deleteEmployee(employeeId);
    }
}