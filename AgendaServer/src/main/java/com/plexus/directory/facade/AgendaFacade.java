package com.plexus.directory.facade;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AgendaFacade {

    //TODO LLLAMADA A GETALL(CON PAGINACION)
    ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size);

    //TODO LLAMADA A GETBYNAME
    ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize);

    //TODO LLAMADA A GETBYSURNAME
    ResponseEntity<EmployeePageResponse> getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize);

    //TODO LLAMADA A GETBYID DE EMPLEADO
    ResponseEntity<EmployeeResponse> getEmployeeById(int employeeId) ;

    //TODO LLAMADA AL ADD EMPLOYEE CON DEVICES
    ResponseEntity<String> createEmployee(List<EmployeeRequest> employeeRequests);

    //TODO LLAMADA AL UPDATE EMPLOYEE CON DEVICES
    ResponseEntity<String> updateEmployee(List<EmployeeRequest> employeeRequests) ;

}
