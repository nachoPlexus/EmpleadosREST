package com.plexus.directory.service.impl;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private Employee employee;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setName("Nacho");
        employee.setSurname("Llorente");
        employee.setMailPlexus("nacho.llorente@plexus.es");
        employee.setPhoneNumber("666666666");

        employees = List.of(
                employee,
                new Employee(2, "Ana", "García", "ana.ana@plexus.es", "777777777", null, null, null),
                new Employee(3, "Luis", "Martínez", "luis.martinez@plexus.es", "688888888", null, null, null)
        );
    }

    @Test
    void testGetAll_Success() {
        when(employeeRepository.getAll(1,200)).thenReturn(employees);

        List<Employee> result = employeeServiceImpl.getAll(1,200);

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(employeeRepository, times(1)).getAll(1,200);
    }

    @Test
    void testGetAll_DataBaseError() {
        when(employeeRepository.getAll(1,200)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeServiceImpl.getAll(1,200));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).getAll(1,200);
    }

    @Test
    void testGetById_Success() {
        when(employeeRepository.get(1)).thenReturn(employee);

        Employee result = employeeServiceImpl.getById(1);

        assertNotNull(result);
        assertEquals("Nacho", result.getName());
        verify(employeeRepository, times(1)).get(1);
    }

    @Test
    void testGetById_DataBaseError() {
        when(employeeRepository.get(1)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeServiceImpl.getById(1));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).get(1);
    }

    @Test
    void testGetByName_Success() {
        when(employeeRepository.get("Nacho",1,200)).thenReturn(employees);

        List<Employee> result = employeeServiceImpl.getByName("Nacho",1,200);
        int randomIndex = new Random().nextInt(result.size());

        assertNotNull(result);
        assertEquals("Nacho", result.get(randomIndex).getName());
        verify(employeeRepository, times(1)).get("Nacho",1,200);
    }

    @Test
    void testGetByName_DataBaseError() {
        when(employeeRepository.get("Nacho",1,200)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeServiceImpl.getByName("Nacho",1,200));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).get("Nacho",1,200);
    }

    @Test
    void testSaveEmployee_Success() {
        when(employeeRepository.save(employee)).thenReturn(1);

        int result = employeeServiceImpl.save(employee);

        assertEquals(1, result);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testSaveEmployee_DataBaseError() {
        when(employeeRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeServiceImpl.save(employee));
        assertTrue(exception.getMessage().contains("DB error"));
    }

    @Test
    void testUpdateEmployee_Success() {
        when(employeeRepository.update(employee)).thenReturn(1);

        int result = employeeServiceImpl.update(employee);

        assertEquals(1, result);
        verify(employeeRepository, times(1)).update(employee);
    }

    @Test
    void testUpdateEmployee_DataBaseError() {
        when(employeeRepository.update(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> employeeServiceImpl.update(employee));
        assertTrue(ex.getMessage().contains("DB error"));
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.delete(employee)).thenReturn(true);

        boolean result = employeeServiceImpl.delete(employee);
        assertTrue(result);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testDeleteEmployee_Failure() {
        when(employeeRepository.delete(employee)).thenReturn(false);

        boolean result = employeeServiceImpl.delete(employee);
        assertFalse(result);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
