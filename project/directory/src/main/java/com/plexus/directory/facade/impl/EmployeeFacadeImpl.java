package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDto;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.facade.EmployeeFacade;
import com.plexus.directory.service.impl.EmployeeServiceImpl;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("versionBase")
public class EmployeeFacadeImpl implements EmployeeFacade {

    private final EmployeeServiceImpl service;
    private final EmployeeMapper mapper;


    public EmployeeFacadeImpl(EmployeeServiceImpl service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size) {
        List<Employee> employees = service.getAll(); // Aquí en realidad llamarías a una versión paginada en el Service
        List<EmployeeDto> employeeDtos = employees.stream().map(mapper::toDto).toList();
        int totalEntities = employees.size(); // Esto debería venir del Service en una implementación paginada

        EmployeePageResponse response = new EmployeePageResponse(employeeDtos, totalEntities, page);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(int employeeId) {
        Employee employee = service.getById(employeeId);
        return ResponseEntity.ok(mapper.toDto(employee));
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName) {

        List<EmployeeDto> employees = service.getByName(employeeName)
                .stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(new EmployeePageResponse(employees, employees.size(), 1));
    }

    @Override
    public ResponseEntity<String> createEmployee(EmployeeDto employeeDTO) {
        int result = service.save(mapper.toEntity(employeeDTO));
        return result > 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }

    @Override
    public ResponseEntity<String> updateEmployee(EmployeeDto employeeDTO) {
        int updated = service.update(mapper.toEntity(employeeDTO));
        return updated > 0 ? ResponseEntity.ok("Employee updated successfully")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }

    @Override
    public ResponseEntity<String> deleteEmployee(int employeeId) {
        Employee employee = service.getById(employeeId);
        if (service.delete(employee)) {
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }
}