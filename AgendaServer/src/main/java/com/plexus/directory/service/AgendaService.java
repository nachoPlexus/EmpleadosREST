package com.plexus.directory.service;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;

import java.util.List;

public interface AgendaService {

    EmployeePageResponse getEmployeesPaged(int page, int size);

    EmployeePageResponse getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize);

    EmployeePageResponse getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize);

    EmployeeResponse getEmployeeById(int employeeId) ;

    String createEmployee(List<EmployeeRequest> employeeRequests);

    String updateEmployee(List<EmployeeRequest> employeeRequests) ;
}
