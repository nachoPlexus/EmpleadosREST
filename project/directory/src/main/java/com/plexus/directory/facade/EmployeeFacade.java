package com.plexus.directory.facade;

import com.plexus.directory.domain.dto.EmployeeDto;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import org.springframework.http.ResponseEntity;

public interface EmployeeFacade {

      ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size);

      ResponseEntity<EmployeeDto> getEmployeeById(int employeeId) ;

      ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName);

      ResponseEntity<String> createEmployee(EmployeeDto employeeDTO);
      ResponseEntity<String> updateEmployee(EmployeeDto employeeDTO) ;

      ResponseEntity<String> deleteEmployee(int employeeId);
}
