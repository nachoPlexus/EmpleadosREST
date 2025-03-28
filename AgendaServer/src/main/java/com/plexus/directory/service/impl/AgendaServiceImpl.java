package com.plexus.directory.service.impl;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.service.AgendaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("versionBase")
public class AgendaServiceImpl implements AgendaService {


    @Override
    public EmployeePageResponse getEmployeesPaged(int page, int size) {
        return null;
    }

    @Override
    public EmployeePageResponse getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize) {
        return null;
    }

    @Override
    public EmployeePageResponse getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize) {
        return null;
    }

    @Override
    public EmployeeResponse getEmployeeById(int employeeId) {
        return null;
    }

    @Override
    public String createEmployee(List<EmployeeRequest> employeeRequests) {
        return "";
    }

    @Override
    public String updateEmployee(List<EmployeeRequest> employeeRequests) {
        return "";
    }
}
