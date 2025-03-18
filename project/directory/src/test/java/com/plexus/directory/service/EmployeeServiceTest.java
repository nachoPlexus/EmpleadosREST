package com.plexus.directory.service;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.error.NoContentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1, "Nacho", "Llorente", "nacho.llorente@plexus.com",null,null,"666666666",null);
    }


    @Test
    void testGetAll_DataBaseError() {
        // Simulamos que el DAO lanza una excepción genérica
        when(employeeRepository.getAll()).thenThrow(new RuntimeException("DB error"));

        // Verificamos que se lanza DataBaseException en el servicio
        DataBaseException ex = assertThrows(
                DataBaseException.class,
                () -> employeeService.getAll()
        );
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
        verify(employeeRepository, times(1)).getAll();
    }

    // ---------------------------------------------------------
    // TEST: getById(int)
    // ---------------------------------------------------------
    @Test
    void testGetById_Success() {
        // Simulamos que el DAO devuelve un empleado
        when(employeeRepository.get(1)).thenReturn(employee);

        Employee result = employeeService.getById(1);

        assertNotNull(result);
        assertEquals("Nacho", result.getName());
        verify(employeeRepository, times(1)).get(1);
    }

    @Test
    void testGetById_NoContent() {
        // Simulamos que el DAO devuelve null
        when(employeeRepository.get(999)).thenReturn(null);

        // Debe lanzar NoContentException
        NoContentException ex = assertThrows(
                NoContentException.class,
                () -> employeeService.getById(999)
        );
        assertTrue(ex.getMessage().contains("No se encontró empleado con ID: 999"));
        verify(employeeRepository, times(1)).get(999);
    }

    @Test
    void testGetById_DataBaseError() {
        // Simulamos que el DAO lanza una excepción
        when(employeeRepository.get(1)).thenThrow(new RuntimeException("DB error"));

        // Verificamos que se lanza DataBaseException
        DataBaseException ex = assertThrows(
                DataBaseException.class,
                () -> employeeService.getById(1)
        );
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
        verify(employeeRepository, times(1)).get(1);
    }

    @Test
    void testGetByName_Success() {
        // Simulamos que el DAO devuelve un empleado
        when(employeeRepository.get("Nacho")).thenReturn(employee);

        Employee result = employeeService.getByName("Nacho");

        assertNotNull(result);
        assertEquals("Nacho", result.getName());
        verify(employeeRepository, times(1)).get("Nacho");
    }

    @Test
    void testGetByName_NameTooLong() {
        // Nombre de 51 caracteres
        String tooLongName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1";

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> employeeService.getByName(tooLongName)
        );
        assertTrue(ex.getMessage().contains("El nombre debe tener una longitud máxima de 50 caracteres"));
        verifyNoInteractions(employeeRepository); // No llega a llamar al DAO
    }

    @Test
    void testGetByName_NoContent() {
        when(employeeRepository.get("Unknown")).thenReturn(null);

        assertThrows(
                NoContentException.class,
                () -> employeeService.getByName("Unknown")
        );
        verify(employeeRepository, times(1)).get("Unknown");
    }

    @Test
    void testGetByName_DataBaseError() {
        when(employeeRepository.get("Nacho")).thenThrow(new RuntimeException("DB error"));

        DataBaseException ex = assertThrows(
                DataBaseException.class,
                () -> employeeService.getByName("Nacho")
        );
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
        verify(employeeRepository, times(1)).get("Nacho");
    }

    @Test
    void testSaveEmployee_Success() {
        when(employeeRepository.save(employee)).thenReturn(1);

        int result = employeeService.save(employee);

        assertEquals(1, result);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testSaveEmployee_DataBaseError() {
        when(employeeRepository.save(any())).thenThrow(new DataBaseException("Error DB"));

        DataBaseException exception = assertThrows(
                DataBaseException.class,
                () -> employeeService.save(employee)
        );
        assertTrue(exception.getMessage().contains("Error DB"));
    }

    @Test
    void testSaveEmployee_NameNull() {
        employee.setName(null);

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> employeeService.save(employee)
        );
        assertTrue(ex.getMessage().contains("El campo 'name' es obligatorio"));
        verifyNoInteractions(employeeRepository);
    }

    @Test
    void testSaveEmployee_NameTooLong() {
        // 51 caracteres xd
        employee.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1");

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> employeeService.save(employee)
        );
        assertTrue(ex.getMessage().contains("El nombre debe tener una longitud máxima de 50 caracteres"));
        verifyNoInteractions(employeeRepository);
    }


    @Test
    void testUpdate_Success() {
        // Simulamos que el DAO actualiza correctamente
        when(employeeRepository.update(employee)).thenReturn(1);

        int result = employeeService.update(employee);

        assertEquals(1, result);
        verify(employeeRepository, times(1)).update(employee);
    }

    @Test
    void testUpdate_DataBaseError() {
        when(employeeRepository.update(any())).thenThrow(new RuntimeException("DB error"));

        DataBaseException ex = assertThrows(
                DataBaseException.class,
                () -> employeeService.update(employee)
        );
        assertTrue(ex.getMessage().contains("error tecnico, pruebe de nuevo"));
    }

    @Test
    void testUpdate_PhoneNumberWrongLength() {
        // 8 dígitos en lugar de 9
        employee.setPhoneNumber("12345678");

        BadRequestException ex = assertThrows(
                BadRequestException.class,
                () -> employeeService.update(employee)
        );
        assertTrue(ex.getMessage().contains("El phoneNumber debe ser numérico de 9 dígitos"));
        verifyNoInteractions(employeeRepository);
    }


    @Test
    void testDelete_Success() {
        when(employeeRepository.delete(employee)).thenReturn(true);

        boolean result = employeeService.delete(employee);
        assertTrue(result);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testDelete_Failure() {
        when(employeeRepository.delete(employee)).thenReturn(false);

        boolean result = employeeService.delete(employee);
        assertFalse(result);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
