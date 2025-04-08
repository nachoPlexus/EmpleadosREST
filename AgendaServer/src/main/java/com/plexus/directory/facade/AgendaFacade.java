package com.plexus.directory.facade;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AgendaFacade {

    ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size);

    ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize);

    ResponseEntity<EmployeePageResponse> getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize);

    ResponseEntity<EmployeeResponse> getEmployeeById(int employeeId) ;

    ResponseEntity<String> add(List<EmployeeRequest> employeeRequests);

    ResponseEntity<String> update(List<EmployeeRequest> employeeRequests) ;

}
