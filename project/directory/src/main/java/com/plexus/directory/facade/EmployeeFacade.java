package com.plexus.directory.facade;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDTO;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.service.EmployeeService;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeFacade {
    private final EmployeeService service;
    private final EmployeeMapper mapper;

    public EmployeeFacade(EmployeeService service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size) {
        List<Employee> employees = service.getAll(); // Aquí en realidad llamarías a una versión paginada en el Service
        List<EmployeeDTO> employeeDTOS = employees.stream().map(mapper::toDTO).toList();
        int totalEntities = employees.size(); // Esto debería venir del Service en una implementación paginada

        EmployeePageResponse response = new EmployeePageResponse(employeeDTOS, totalEntities, page);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<EmployeeDTO> getEmployeeById(int employeeId) {
        Employee employee = service.getById(employeeId);
        return ResponseEntity.ok(mapper.toDTO(employee));
    }

    public ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName) {

        List<Employee> employees = service.getByName(employeeName);
        List<EmployeeDTO> employeeDTOS = employees.stream().map(mapper::toDTO).toList();


        return ResponseEntity.ok(new EmployeePageResponse(employeeDTOS, employeeDTOS.size(), 1));
    }

    public ResponseEntity<String> createEmployee(EmployeeDTO employeeDTO) {
        int result = service.save(mapper.toEntity(employeeDTO));
        return result > 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }

    public ResponseEntity<String> updateEmployee(EmployeeDTO employeeDTO) {
        int updated = service.update(mapper.toEntity(employeeDTO));
        return updated > 0 ? ResponseEntity.ok("Employee updated successfully")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }

    public ResponseEntity<String> deleteEmployee(int employeeId) {
        Employee employee = service.getById(employeeId);
        if (service.delete(employee)) {
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }
}