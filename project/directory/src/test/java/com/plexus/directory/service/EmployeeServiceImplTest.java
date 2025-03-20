package com.plexus.directory.service;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
        employee = new Employee(1, "Nacho", "Llorente", "nacho.llorente@plexus.com", null, null, "666666666", null);
        employees = Arrays.asList(new Employee(1, "Nacho", "Gómez", "nacho@plexus.com", "nacho@cliente.com", "12345", "600123456", "SN12345"), new Employee(2, "Nacho", "Fernández", "nacho2@plexus.com", "nacho2@cliente.com", "67890", "600654321", "SN67890"), employee);
    }


    @Test
    void testGetAll_DataBaseError() {
        when(employeeRepository.getAll()).thenThrow(new RuntimeException("DB error"));

        DataBaseException ex = assertThrows(DataBaseException.class, () -> employeeServiceImpl.getAll());
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
        verify(employeeRepository, times(1)).getAll();
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

        DataBaseException ex = assertThrows(DataBaseException.class, () -> employeeServiceImpl.getById(1));
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
        verify(employeeRepository, times(1)).get(1);
    }

    @Test
    void testGetByName_Success() {
        when(employeeRepository.get("Nacho")).thenReturn(employees);

        List<Employee> result = employeeServiceImpl.getByName("Nacho");
        int randomIndex = new Random().nextInt(result.size());

        assertNotNull(result);
        assertEquals("Nacho", result.get(randomIndex).getName());
        verify(employeeRepository, times(1)).get("Nacho");
    }

    @Test
    void testGetByName_NameTooLong() {
        // 51 caracteres xd
        String tooLongName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1";

        BadRequestException ex = assertThrows(BadRequestException.class, () -> employeeServiceImpl.getByName(tooLongName));
        assertTrue(ex.getMessage().contains("El nombre debe tener una longitud máxima de 50 caracteres"));
        verifyNoInteractions(employeeRepository); // No llega a llamar al DAO
    }

    @Test
    void testGetByName_Database() {
        when(employeeRepository.get("Nacho")).thenReturn(null);

        assertThrows(DataBaseException.class, () -> employeeServiceImpl.getByName("Nacho"));
        verify(employeeRepository, times(1)).get("Nacho");
    }

    @Test
    void testGetByName_DataBaseError() {
        when(employeeRepository.get("Nacho")).thenThrow(new RuntimeException("DB error"));

        DataBaseException ex = assertThrows(DataBaseException.class, () -> employeeServiceImpl.getByName("Nacho"));
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
        verify(employeeRepository, times(1)).get("Nacho");
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
        when(employeeRepository.save(any())).thenThrow(new DataBaseException("Error DB"));

        DataBaseException exception = assertThrows(DataBaseException.class, () -> employeeServiceImpl.save(employee));
        assertTrue(exception.getMessage().contains("Error DB"));
    }

    @Test
    void testSaveEmployee_NameNull() {
        employee.setName(null);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> employeeServiceImpl.save(employee));
        assertTrue(ex.getMessage().contains("El campo 'name' es obligatorio"));
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void testSaveEmployee_NameTooLong() {
        // 51 caracteres xd
        employee.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1");

        BadRequestException ex = assertThrows(BadRequestException.class, () -> employeeServiceImpl.save(employee));
        assertTrue(ex.getMessage().contains("El nombre debe tener una longitud máxima de 50 caracteres"));
        verifyNoInteractions(employeeRepository);
    }


    @Test
    void testUpdate_Success() {
        when(employeeRepository.update(employee)).thenReturn(1);

        int result = employeeServiceImpl.update(employee);

        assertEquals(1, result);
        verify(employeeRepository, times(1)).update(employee);
    }

    @Test
    void testUpdate_DataBaseError() {
        when(employeeRepository.update(any())).thenThrow(new RuntimeException("DB error"));

        DataBaseException ex = assertThrows(DataBaseException.class, () -> employeeServiceImpl.update(employee));
        assertTrue(ex.getMessage().contains("DB error"));
    }

    @Test
    void testUpdate_PhoneNumberWrongLength() {
        employee.setPhoneNumber("12345678");

        BadRequestException ex = assertThrows(BadRequestException.class, () -> employeeServiceImpl.update(employee));
        assertTrue(ex.getMessage().contains("El phoneNumber debe ser numérico de 9 dígitos"));
        verifyNoInteractions(employeeRepository);
    }


    @Test
    void testDelete_Success() {
        when(employeeRepository.delete(employee)).thenReturn(true);

        boolean result = employeeServiceImpl.delete(employee);
        assertTrue(result);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testDelete_Failure() {
        when(employeeRepository.delete(employee)).thenReturn(false);

        boolean result = employeeServiceImpl.delete(employee);
        assertFalse(result);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
