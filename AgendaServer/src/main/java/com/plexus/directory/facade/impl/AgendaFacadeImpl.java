package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.facade.AgendaFacade;
import com.plexus.directory.service.impl.AgendaServiceAsync;
import com.plexus.directory.service.impl.AgendaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.List;


@Component
@EnableAsync
@Slf4j
public class AgendaFacadeImpl implements AgendaFacade {
    private final AgendaServiceImpl service;
    private final AgendaServiceAsync asyncService;
    private final View error;

    @Autowired
    public AgendaFacadeImpl(AgendaServiceImpl service, AgendaServiceAsync asyncService, View error) {
        this.service = service;
        this.asyncService = asyncService;
        this.error = error;
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size) {
        EmployeePageResponse employees = service.getEmployeesPaged(page,size);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize) {
        EmployeePageResponse employees = service.getEmployeesByName(employeeName, resolvedPage, resolvedSize);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize) {
        EmployeePageResponse employees = service.getEmployeesBySurname(employeeSurname, resolvedPage, resolvedSize);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(int employeeId) {
        EmployeeResponse employees = service.getEmployeeById(employeeId);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<String> createEmployees(List<EmployeeRequest> employeeRequests) {
        String result = service.createEmployee(employeeRequests);
        return result.equals("ok") ? ResponseEntity.status(HttpStatus.CREATED).body("Todos los epmloyees creados con sus respectivos devices")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error inesperado");
    }

    @Override
    public ResponseEntity<String> updateEmployee(List<EmployeeRequest> employeeRequests) {
        String result = service.createEmployee(employeeRequests);
        return result.equals("ok") ? ResponseEntity.status(HttpStatus.CREATED).body("Todos los epmloyees actualizados con sus respectivos devices")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error inesperado");
    }
}
