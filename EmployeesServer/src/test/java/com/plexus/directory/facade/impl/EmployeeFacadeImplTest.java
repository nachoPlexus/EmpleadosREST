package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDto;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeFacadeImplTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeFacadeImpl employeeFacade;

    private Employee employee;
    private EmployeeDto employeeDto;
    private List<Employee> employeeList;
    private List<EmployeeDto> employeeDtoList;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Nacho", "Llorente", "nacho.llorente@plexus.es", "666666666", null, null, null);
        employeeDto = new EmployeeDto(1, "Nacho", "Llorente", "nacho.llorente@plexus.es", "666666666", null, null, null);

        employeeList = List.of(
                employee,
                new Employee(2, "Ana", "García", "ana.ana@plexus.es", "777777777", null, null, null),
                new Employee(3, "Luis", "Martínez", "luis.martinez@plexus.es", "688888888", null, null, null)
        );

        employeeDtoList = List.of(
                employeeDto,
                new EmployeeDto(2, "Ana", "García", "ana.ana@plexus.es", "777777777", null, null, null),
                new EmployeeDto(3, "Luis", "Martínez", "luis.martinez@plexus.es", "688888888", null, null, null)
        );
    }

    @Test
    void testGetEmployeesPaged_Success() {
        when(employeeService.getAll(1,10)).thenReturn(employeeList);
        when(employeeMapper.toDto(any())).thenReturn(employeeDto);

        ResponseEntity<EmployeePageResponse> response = employeeFacade.getEmployeesPaged(1, 10);

        assertNotNull(response);
        assertEquals(3, response.getBody().getEmployees().stream().count());
        verify(employeeService, times(1)).getAll(1,10);
        verify(employeeMapper, times(3)).toDto(any());
    }

    @Test
    void testGetEmployeeById_Success() {
        when(employeeService.getById(1)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeFacade.getEmployeeById(1);

        assertNotNull(response);
        assertEquals("Nacho", response.getBody().getName());
        verify(employeeService, times(1)).getById(1);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void testGetEmployeesByName_Success() {
        when(employeeService.getByName("Nacho",1,200)).thenReturn(employeeList);
        when(employeeMapper.toDto(any())).thenReturn(employeeDto);

        ResponseEntity<EmployeePageResponse> response = employeeFacade.getEmployeesByName("Nacho",1,200);

        assertNotNull(response);
        assertEquals(3, response.getBody().getEmployees().stream().count());
        verify(employeeService, times(1)).getByName("Nacho",1,200);
        verify(employeeMapper, times(3)).toDto(any());
    }

    @Test
    void testCreateEmployee_Success() {
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeService.save(employee)).thenReturn(1);

        ResponseEntity<String> response = employeeFacade.createEmployee(employeeDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee creado bien", response.getBody());
        verify(employeeService, times(1)).save(employee);
    }

    @Test
    void testCreateEmployee_Failure() {
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeService.save(employee)).thenReturn(0);

        ResponseEntity<String> response = employeeFacade.createEmployee(employeeDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error inesperado", response.getBody());
    }

    @Test
    void testUpdateEmployee_Success() {
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeService.update(employee)).thenReturn(1);

        ResponseEntity<String> response = employeeFacade.updateEmployee(employeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee actualizado bien", response.getBody());
        verify(employeeService, times(1)).update(employee);
    }

    @Test
    void testUpdateEmployee_Failure() {
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeService.update(employee)).thenReturn(0);

        ResponseEntity<String> response = employeeFacade.updateEmployee(employeeDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error inesperado", response.getBody());
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeService.getById(1)).thenReturn(employee);
        when(employeeService.delete(employee)).thenReturn(true);

        ResponseEntity<String> response = employeeFacade.deleteEmployee(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee borrado bien", response.getBody());
        verify(employeeService, times(1)).delete(employee);
    }

    @Test
    void testDeleteEmployee_Failure() {
        when(employeeService.getById(1)).thenReturn(employee);
        when(employeeService.delete(employee)).thenReturn(false);

        ResponseEntity<String> response = employeeFacade.deleteEmployee(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("error inesperado", response.getBody());
    }
}
