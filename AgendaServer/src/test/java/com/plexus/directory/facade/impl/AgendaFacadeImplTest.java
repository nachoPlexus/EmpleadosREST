package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.model.request.DeviceRequest;
import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.service.impl.AgendaServiceAsync;
import com.plexus.directory.service.impl.AgendaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.View;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendaFacadeImplTest {

    @Mock
    private AgendaServiceImpl service;
    @Mock
    private AgendaServiceAsync asyncService;
    @Mock
    private Validator validator;
    @Mock
    private View error;

    @InjectMocks
    private AgendaFacadeImpl facade;

    private EmployeePageResponse employeePageResponse;
    private EmployeeResponse employeeResponse;
    private List<EmployeeRequest> employeeRequests;
    private List<EmployeeRequest> validEmployeeRequests;
    private List<EmployeeRequest> invalidEmployeeRequests;

    @BeforeEach
    void setUp() {
        employeeResponse = new EmployeeResponse(1, "John", "Doe", "plexus@mail.com", "client@mail.com", 666777888, null);
        employeePageResponse = new EmployeePageResponse(List.of(employeeResponse), 1, 1);

        validEmployeeRequests = List.of(
                new EmployeeRequest(1, "John", "Doe", "plexus@mail.com", "client@mail.com", "666777888", null, false, false)
        );

        invalidEmployeeRequests = List.of(
                new EmployeeRequest(2, "", "Doe", "notanemail", "", "abc123",
                        new DeviceRequest("SN", "", "Model", "OS"), false, false) // Datos inválidos
        );
        employeeRequests = List.of(new EmployeeRequest(1, "John", "Doe", "plexus@mail.com", "client@mail.com", "666777888", null, false, false));

    }

    @Test
    void getEmployeesPaged_Success() {
        when(service.getEmployeesPaged(1, 10)).thenReturn(employeePageResponse);

        ResponseEntity<EmployeePageResponse> response = facade.getEmployeesPaged(1, 10);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getPageNumber());
        verify(service).getEmployeesPaged(1, 10);
    }

    @Test
    void getEmployeesByName_Success() {
        when(service.getEmployeesByName("John", 1, 10)).thenReturn(employeePageResponse);

        ResponseEntity<EmployeePageResponse> response = facade.getEmployeesByName("John", 1, 10);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getEmployees().size());
        verify(service).getEmployeesByName("John", 1, 10);
    }

    @Test
    void getEmployeesBySurname_Success() {
        when(service.getEmployeesBySurname("Doe", 1, 10)).thenReturn(employeePageResponse);

        ResponseEntity<EmployeePageResponse> response = facade.getEmployeesBySurname("Doe", 1, 10);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getEmployees().size());
        verify(service).getEmployeesBySurname("Doe", 1, 10);
    }

    @Test
    void getEmployeeById_Success() {
        when(service.getEmployeeById(1)).thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response = facade.getEmployeeById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getEmployeeName());
        verify(service).getEmployeeById(1);
    }

    @Test
    void update_Failure() {
        when(service.updateEmployee(employeeRequests)).thenReturn("error");

        ResponseEntity<String> response = facade.update(employeeRequests);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        verify(service).updateEmployee(employeeRequests);
    }

    @Test
    void add_Failure() {
        when(service.createEmployee(employeeRequests)).thenReturn("error");

        ResponseEntity<String> response = facade.add(employeeRequests);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        verify(service).createEmployee(employeeRequests);
    }


    @Test
    void add_Success() {
        when(service.createEmployee(validEmployeeRequests)).thenReturn("ok");

        ResponseEntity<String> response = facade.add(validEmployeeRequests);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Todos los empleados creados correctamente", response.getBody());
        verify(service).createEmployee(validEmployeeRequests);
    }

    @Test
    void update_Success() {
        when(service.updateEmployee(validEmployeeRequests)).thenReturn("ok");

        ResponseEntity<String> response = facade.update(validEmployeeRequests);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Todos los empleados actualizados correctamente", response.getBody());
        verify(service).updateEmployee(validEmployeeRequests);
    }



}

