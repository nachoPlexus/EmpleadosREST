package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.facade.AgendaFacade;
import com.plexus.directory.service.impl.AgendaServiceAsync;
import com.plexus.directory.service.impl.AgendaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.List;


@Component
@Profile("versionBase")
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
        return null;
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize) {
        return null;
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize) {
        return null;
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(int employeeId) {
        return null;
    }

    @Override
    public ResponseEntity<String> createEmployee(List<EmployeeRequest> employeeRequests) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateEmployee(List<EmployeeRequest> employeeRequests) {
        return null;
    }
}
