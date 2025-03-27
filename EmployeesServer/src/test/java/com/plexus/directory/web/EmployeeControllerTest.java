package com.plexus.directory.web;

import com.plexus.directory.domain.dto.EmployeeDto;
import com.plexus.directory.domain.dto.EmployeePageResponse;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.NoContentException;
import com.plexus.directory.facade.impl.EmployeeFacadeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeFacadeImpl facade;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDto employeeDto;
    private List<EmployeeDto> employees;

    @BeforeEach
    void setUp() {
        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("Nacho");
        employeeDto.setSurname("Llorente");
        employeeDto.setMailPlexus("nacho.llorente@plexus.es");
        employeeDto.setPhoneNumber("666666666");

        employees = List.of(
                employeeDto,
                new EmployeeDto(2, "Ana", "García", "ana.ana@plexus.es", "777777777", null, null, null),
                new EmployeeDto(3, "Luis", "Martínez", "luis.martinez@plexus.es", "688888888", null, null, null)
        );

    }

    @Test
    void getEmployees() {
        when(facade.getEmployeesPaged(0, 10)).thenReturn(ResponseEntity.ok(new EmployeePageResponse(employees, employees.size(), 1)));

        ResponseEntity<EmployeePageResponse> response = employeeController.getEmployees(1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(facade, times(1)).getEmployeesPaged(0, 10);
    }

    @Test
    void getEmployeeById() {
        when(facade.getEmployeeById(1)).thenReturn(ResponseEntity.ok(employeeDto));

        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nacho", response.getBody().getName());
        verify(facade, times(1)).getEmployeeById(1);
    }

    @Test
    void getEmployeesByName() {
        when(facade.getEmployeesByName("Nacho",0,200)).thenReturn(ResponseEntity.ok(new EmployeePageResponse(employees, employees.size(), 1)));

        ResponseEntity<EmployeePageResponse> response = employeeController.getEmployeesByName("Nacho",1,200);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(facade, times(1)).getEmployeesByName("Nacho",0,200);
    }

    @Test
    void getEmployeeById_NotFound() {
        when(facade.getEmployeeById(99)).thenThrow(new NoContentException("Empleado no encontrado"));

        Exception exception = assertThrows(NoContentException.class, () ->
                employeeController.getEmployeeById(99)
        );

        assertEquals("Empleado no encontrado", exception.getMessage());
        verify(facade, times(1)).getEmployeeById(99);
    }

    @Test
    void getEmployeesByName_EmptyResponse() {
        when(facade.getEmployeesByName("Unknown",0,200)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<EmployeePageResponse> response = employeeController.getEmployeesByName("Unknown",1,200);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(facade, times(1)).getEmployeesByName("Unknown",0,200);
    }

    @Test
    void createEmployee() {
        when(facade.createEmployee(employeeDto)).thenReturn(ResponseEntity.ok("Empleado creado correctamente"));

        ResponseEntity<String> response = employeeController.createEmployee(employeeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Empleado creado correctamente", response.getBody());
        verify(facade, times(1)).createEmployee(employeeDto);
    }

    @Test
    void updateEmployee() {
        when(facade.updateEmployee(employeeDto)).thenReturn(ResponseEntity.ok("Empleado actualizado correctamente"));

        ResponseEntity<String> response = employeeController.updateEmployee(employeeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Empleado actualizado correctamente", response.getBody());
        verify(facade, times(1)).updateEmployee(employeeDto);
    }

    @Test
    void deleteEmployee() {
        when(facade.deleteEmployee(1)).thenReturn(ResponseEntity.ok("Empleado eliminado correctamente"));
        
        ResponseEntity<String> response = employeeController.deleteEmployee(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Empleado eliminado correctamente", response.getBody());
        verify(facade, times(1)).deleteEmployee(1);
    }


    @Test
    void deleteEmployee_NotFound() {
        when(facade.deleteEmployee(99)).thenThrow(new NoContentException("Empleado no encontrado"));

        Exception exception = assertThrows(NoContentException.class, () ->
                employeeController.deleteEmployee(99)
        );

        assertEquals("Empleado no encontrado", exception.getMessage());
        verify(facade, times(1)).deleteEmployee(99);
    }



    // validaciones con Jakarta
    // DTO con datos malos malisimos
    @Test
    void createEmployee_InvalidData() {
        EmployeeDto invalidEmployeeDto = new EmployeeDto();
        invalidEmployeeDto.setName(""); // Nombre inválido

        when(facade.createEmployee(invalidEmployeeDto)).thenThrow(new IllegalArgumentException("Your request was invalid :El nombre es obligatorio"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            employeeController.createEmployee(invalidEmployeeDto)
        );

        assertEquals("Your request was invalid :El nombre es obligatorio", exception.getMessage());
        verify(facade, times(1)).createEmployee(invalidEmployeeDto);
    }

    @Test
    void updateEmployee_InvalidData() {
        EmployeeDto invalidEmployeeDto = new EmployeeDto();
        invalidEmployeeDto.setPhoneNumber("123"); // Número demasiado corto

        when(facade.updateEmployee(invalidEmployeeDto)).thenThrow(new BadRequestException("Número de teléfono inválido"));

        Exception exception = assertThrows(BadRequestException.class, () ->
                employeeController.updateEmployee(invalidEmployeeDto)
        );

        assertEquals("Your request was invalid :Número de teléfono inválido", exception.getMessage());
        verify(facade, times(1)).updateEmployee(invalidEmployeeDto);
    }

    @Test
    void createEmployee_ValidationError() {
        EmployeeDto invalidEmployeeDto = new EmployeeDto();
        invalidEmployeeDto.setName(""); // Campo vacío

        when(facade.createEmployee(invalidEmployeeDto)).thenThrow(new BadRequestException("El nombre es obligatorio"));

        Exception exception = assertThrows(BadRequestException.class, () ->
                employeeController.createEmployee(invalidEmployeeDto)
        );

        assertEquals("Your request was invalid :El nombre es obligatorio", exception.getMessage());
        verify(facade, times(1)).createEmployee(invalidEmployeeDto);
    }


}