package com.plexus.directory.web;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.facade.AgendaFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AgendaControllerTest {

    @Mock
    private AgendaFacade facade;

    @InjectMocks
    private AgendaController controller;

    private MockMvc mockMvc;
    private EmployeePageResponse employeePageResponse;
    private EmployeeResponse employeeResponse;
    private List<EmployeeRequest> employeeRequests;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        employeeResponse = new EmployeeResponse(1, "John", "Doe", "plexus@mail.com", "client@mail.com", 666777888, null);
        employeePageResponse = new EmployeePageResponse(Collections.singletonList(employeeResponse), 1, 1);
        employeeRequests = Collections.singletonList(new EmployeeRequest(1, "John", "Doe", "plexus@mail.com", "client@mail.com", "666777888", null, false, false));
    }

    @Test
    void getEmployeesPaged_Success() throws Exception {
        when(facade.getEmployeesPaged(0, 10)).thenReturn(ResponseEntity.ok(employeePageResponse));

        mockMvc.perform(get("/agenda/employees/1/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.employees.length()").value(1));

        verify(facade).getEmployeesPaged(0, 10);
    }

    @Test
    void getEmployeesByName_Success() throws Exception {
        when(facade.getEmployeesByName("John", 1, 10)).thenReturn(ResponseEntity.ok(employeePageResponse));

        mockMvc.perform(get("/agenda/employees/filter/John?filterType=name&page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees.length()").value(1));

        verify(facade).getEmployeesByName("John", 1, 10);
    }

    @Test
    void getEmployeesBySurname_Success() throws Exception {
        when(facade.getEmployeesBySurname("Doe", 1, 10)).thenReturn(ResponseEntity.ok(employeePageResponse));

        mockMvc.perform(get("/agenda/employees/filter/Doe?filterType=surname&page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees.length()").value(1));

        verify(facade).getEmployeesBySurname("Doe", 1, 10);
    }

    @Test
    void getEmployeeById_Success() throws Exception {
        when(facade.getEmployeeById(1)).thenReturn(ResponseEntity.ok(employeeResponse));

        mockMvc.perform(get("/agenda/employees/filter/1?filterType=id&page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].employeeName").value("John"));

        verify(facade).getEmployeeById(1);
    }

    @Test
    void createEmployees_Success() throws Exception {
        when(facade.createEmployees(employeeRequests)).thenReturn(ResponseEntity.status(201).body("Todos los employees creados con sus respectivos devices"));

        mockMvc.perform(post("/agenda/employees")
                        .contentType("application/json")
                        .content("[{\"id\":1,\"name\":\"John\",\"surname\":\"Doe\",\"plexusMail\":\"plexus@mail.com\",\"clientMail\":\"client@mail.com\",\"phoneNumber\":\"666777888\"}]"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Todos los employees creados con sus respectivos devices"));

        verify(facade).createEmployees(anyList());
    }

    @Test
    void updateEmployees_Success() throws Exception {
        when(facade.updateEmployee(employeeRequests)).thenReturn(ResponseEntity.status(201).body("Todos los employees actualizados con sus respectivos devices"));

        mockMvc.perform(put("/agenda/employees")
                        .contentType("application/json")
                        .content("[{\"id\":1,\"name\":\"John\",\"surname\":\"Doe\",\"plexusMail\":\"plexus@mail.com\",\"clientMail\":\"client@mail.com\",\"phoneNumber\":\"666777888\"}]"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Todos los employees actualizados con sus respectivos devices"));

        verify(facade).updateEmployee(anyList());
    }
}
