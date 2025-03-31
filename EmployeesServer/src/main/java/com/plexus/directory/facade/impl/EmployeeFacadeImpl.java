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

import static com.plexus.directory.common.Constants.INVISIBLE;

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
        return CompletableFuture.supplyAsync(service::getTotalEmployees);
    }

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(int employeeId) {
        Employee employee = service.getById(employeeId);
        return ResponseEntity.ok(mapper.toDto(employee));
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName,int page, int size) {

        List<EmployeeDto> employees = service.getByName(employeeName,page,size)
                .stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(new EmployeePageResponse(employees, employees.size(), page));
    }


    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesbySurname(String surnameValue, int page, int size) {

        List<EmployeeDto> employees = service.getBySurname(surnameValue,page,size)
                .stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(new EmployeePageResponse(employees, employees.size(), page));
    }

    @Override
    public ResponseEntity<String> createEmployee(EmployeeDto employeeDTO) {
        int result = service.save(mapper.toEntity(employeeDTO));
        return result > 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Employee creado bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error inesperado");
    }

    @Override
    public ResponseEntity<String> updateEmployee(EmployeeDto employeeDTO) {
        int updated = service.update(mapper.toEntity(employeeDTO));
        return updated > 0 ? ResponseEntity.ok("Employee actualizado bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error inesperado");
    }

    @Override
    public ResponseEntity<String> deleteEmployee(int employeeId) {
        Employee employee = service.getById(employeeId);

        return service.delete(employee) ? ResponseEntity.ok("Employee eliminado bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error inesperado");
    }

    @Override
    public ResponseEntity<String> updateAllSurnamesToCamelCase() {
        int updated = service.updateAllSurnamesToCamelCase();
        return updated > 0 ? ResponseEntity.ok("Employees actualizados bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error inesperado");
    }

}