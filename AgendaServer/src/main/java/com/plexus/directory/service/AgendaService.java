package com.plexus.directory.service;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;

import java.util.List;

public interface AgendaService {

    //TODO LLLAMADA A GETALL(CON PAGINACION)
    EmployeePageResponse getEmployeesPaged(int page, int size);

    //TODO LLAMADA A GETBYNAME
    EmployeePageResponse getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize);

    //TODO LLAMADA A GETBYSURNAME
    EmployeePageResponse getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize);

    //TODO LLAMADA A GETBYID DE EMPLEADO
    EmployeeResponse getEmployeeById(int employeeId) ;

    //TODO LLAMADA AL ADD EMPLOYEE CON DEVICES
    String createEmployee(List<EmployeeRequest> employeeRequests);

    //TODO LLAMADA AL UPDATE EMPLOYEE CON DEVICES
    String updateEmployee(List<EmployeeRequest> employeeRequests) ;

}
