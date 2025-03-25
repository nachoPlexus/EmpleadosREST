package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDto;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.facade.EmployeeFacade;
import com.plexus.directory.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@Profile("versionBase")
@EnableAsync
public class EmployeeFacadeImpl implements EmployeeFacade {

    private final EmployeeServiceImpl service;
    private final EmployeeMapper mapper;


    public EmployeeFacadeImpl(EmployeeServiceImpl service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size) {
        CompletableFuture<Integer> totalSizeFuture = getEmployeeCountAsync();

        List<Employee> employees = service.getAll(page,size);
        List<EmployeeDto> employeeDtos = employees.stream().map(mapper::toDto).toList();

        int totalEntities;
        try {
            totalEntities = totalSizeFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error al contar los employees totales: \n"+e.getMessage());
            totalEntities = employees.size();
        }

        EmployeePageResponse response = new EmployeePageResponse(employeeDtos, totalEntities, page+1);
        return ResponseEntity.ok(response);
    }
    @Async
    public CompletableFuture<Integer> getEmployeeCountAsync() {
        return CompletableFuture.supplyAsync(service::getTotalEmployees);//LO HAGO CON ESTO PORQUE EL AsyncResult ESTA DEPRECADO
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