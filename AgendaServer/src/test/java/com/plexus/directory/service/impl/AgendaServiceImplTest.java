package com.plexus.directory.service.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private DeviceMapper deviceMapper;

    @InjectMocks
    private AgendaServiceImpl agendaServiceImpl;

    // Objetos dummy para usar en los tests
    private EmployeeDto dummyEmployeeDto;
    private EmployeeResponse dummyEmployeeResponse;
    private EmployeePageResponse dummyEmployeePageResponse;
    private DeviceDto dummyDeviceDto;

    @BeforeEach
    void setUp() {
        // Inicializamos un empleado dummy
        dummyEmployeeDto = new EmployeeDto();
        dummyEmployeeDto.setId(1);
        dummyEmployeeDto.setName("Nacho");
        dummyEmployeeDto.setSurname("Llorente");
        // Inicializamos la respuesta correspondiente
        dummyEmployeeResponse = new EmployeeResponse();
        dummyEmployeeResponse.setEmployeeId(1);
        dummyEmployeeResponse.setName("Nacho");
        dummyEmployeeResponse.setSurname("Llorente");
        // Suponemos que el empleado en la respuesta inicialmente no tiene dispositivo asignado
        dummyEmployeeResponse.setAssignedDevice(null);

        // Creamos una lista y la asignamos a la página de respuesta
        List<EmployeeResponse> employeesResponseList = new ArrayList<>();
        employeesResponseList.add(dummyEmployeeResponse);
        dummyEmployeePageResponse = new EmployeePageResponse();
        dummyEmployeePageResponse.setEmployees(employeesResponseList);

        // Creamos un dispositivo dummy para la asignación
        dummyDeviceDto = new DeviceDto();
        // (Configura los atributos necesarios de dummyDeviceDto)
    }

    // Test para getEmployeesPaged
    @Test
    void testGetEmployeesPaged_Success() {
        int page = 1, size = 10;
        // Simula que la consulta devuelve una lista con un solo EmployeeDto
        List<EmployeeDto> dtoList = List.of(dummyEmployeeDto);
        when(employeeRepository.getAll(page, size)).thenReturn(dtoList);
        when(employeeMapper.toResponse(dtoList)).thenReturn(dummyEmployeePageResponse);
        // Simula la asignación del dispositivo para el empleado con id 1
        when(deviceRepository.getAssignation(1)).thenReturn(dummyDeviceDto);
        when(deviceMapper.toResponse(dummyDeviceDto)).thenReturn("DeviceAssigned");

        EmployeePageResponse result = agendaServiceImpl.getEmployeesPaged(page, size);

        assertNotNull(result);
        assertEquals(1, result.getEmployees().size());
        assertEquals("DeviceAssigned", result.getEmployees().get(0).getAssignedDevice());
        verify(employeeRepository, times(1)).getAll(page, size);
        verify(deviceRepository, times(1)).getAssignation(1);
    }

    @Test
    void testGetEmployeesPaged_DataBaseError() {
        int page = 1, size = 10;
        when(employeeRepository.getAll(page, size)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> agendaServiceImpl.getEmployeesPaged(page, size));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).getAll(page, size);
    }

    // Test para getEmployeesByName
    @Test
    void testGetEmployeesByName_Success() {
        String name = "Nacho";
        int page = 1, size = 10;
        List<EmployeeDto> dtoList = List.of(dummyEmployeeDto);
        when(employeeRepository.getByEmployeeName(name, page, size)).thenReturn(dtoList);
        when(employeeMapper.toResponse(dtoList)).thenReturn(dummyEmployeePageResponse);
        when(deviceRepository.getAssignation(1)).thenReturn(dummyDeviceDto);
        when(deviceMapper.toResponse(dummyDeviceDto)).thenReturn("DeviceAssigned");

        EmployeePageResponse result = agendaServiceImpl.getEmployeesByName(name, page, size);
        assertNotNull(result);
        assertEquals(1, result.getEmployees().size());
        assertEquals("DeviceAssigned", result.getEmployees().get(0).getAssignedDevice());
        verify(employeeRepository, times(1)).getByEmployeeName(name, page, size);
    }

    @Test
    void testGetEmployeesByName_DataBaseError() {
        String name = "Nacho";
        int page = 1, size = 10;
        when(employeeRepository.getByEmployeeName(name, page, size)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> agendaServiceImpl.getEmployeesByName(name, page, size));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).getByEmployeeName(name, page, size);
    }

    // Test para getEmployeesBySurname
    @Test
    void testGetEmployeesBySurname_Success() {
        String surname = "Llorente";
        int page = 1, size = 10;
        List<EmployeeDto> dtoList = List.of(dummyEmployeeDto);
        when(employeeRepository.getByEmployeeSurname(surname, page, size)).thenReturn(dtoList);
        when(employeeMapper.toResponse(dtoList)).thenReturn(dummyEmployeePageResponse);
        when(deviceRepository.getAssignation(1)).thenReturn(dummyDeviceDto);
        when(deviceMapper.toResponse(dummyDeviceDto)).thenReturn("DeviceAssigned");

        EmployeePageResponse result = agendaServiceImpl.getEmployeesBySurname(surname, page, size);
        assertNotNull(result);
        assertEquals(1, result.getEmployees().size());
        assertEquals("DeviceAssigned", result.getEmployees().get(0).getAssignedDevice());
        verify(employeeRepository, times(1)).getByEmployeeSurname(surname, page, size);
    }

    @Test
    void testGetEmployeesBySurname_DataBaseError() {
        String surname = "Llorente";
        int page = 1, size = 10;
        when(employeeRepository.getByEmployeeSurname(surname, page, size)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> agendaServiceImpl.getEmployeesBySurname(surname, page, size));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).getByEmployeeSurname(surname, page, size);
    }

    // Test para getEmployeeById
    @Test
    void testGetEmployeeById_Success() {
        int id = 1;
        when(employeeRepository.getEmployeeById(id)).thenReturn(dummyEmployeeDto);
        when(employeeMapper.toResponse(dummyEmployeeDto)).thenReturn(dummyEmployeeResponse);
        when(deviceRepository.getAssignation(dummyEmployeeResponse.getEmployeeId())).thenReturn(dummyDeviceDto);
        when(deviceMapper.toResponse(dummyDeviceDto)).thenReturn("DeviceAssigned");

        EmployeeResponse result = agendaServiceImpl.getEmployeeById(id);
        assertNotNull(result);
        assertEquals("Nacho", result.getEmployeeName());
        assertEquals("DeviceAssigned", result.getAssignedDevice());
        verify(employeeRepository, times(1)).getEmployeeById(id);
    }

    @Test
    void testGetEmployeeById_DataBaseError() {
        int id = 1;
        when(employeeRepository.getEmployeeById(id)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> agendaServiceImpl.getEmployeeById(id));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(employeeRepository, times(1)).getEmployeeById(id);
    }

    @Test
    void testCreateEmployee_Success() {
        EmployeeRequest request = new EmployeeRequest();
        request.setId(1);
        request.setName("Nacho");
        when(employeeMapper.toDto(request)).thenReturn(dummyEmployeeDto);
        doNothing().when(employeeRepository).save(anyList());
        doNothing().when(deviceRepository).save(anyList());
        String result = agendaServiceImpl.createEmployee(List.of(request));
        assertEquals("ok", result);
        verify(employeeRepository, times(1)).save(anyList());
        verify(deviceRepository, times(1)).save(anyList());
    }

    @Test
    void testUpdateEmployee_Success() {
        EmployeeRequest requestUpdate = new EmployeeRequest();
        requestUpdate.setId(1);
        requestUpdate.setName("Nacho Updated");
        requestUpdate.setDeleteEmployee(false);
        requestUpdate.setDeleteAssignedDevice(false);

        EmployeeRequest requestDelete = new EmployeeRequest();
        requestDelete.setId(2);
        requestDelete.setDeleteEmployee(true);
        requestDelete.setDeleteAssignedDevice(true);
        when(deviceRepository.getAssignation(requestDelete.getId())).thenReturn(dummyDeviceDto);
        when(employeeMapper.toDto(requestUpdate)).thenReturn(dummyEmployeeDto);
        when(employeeMapper.toDto(requestDelete)).thenReturn(dummyEmployeeDto);
        when(deviceRepository.update(anyList())).thenReturn(1);
        when(employeeRepository.update(anyList())).thenReturn(1);
        when(employeeRepository.delete(anyList())).thenReturn(1);
        when(deviceRepository.save(anyList())).thenReturn(1);

        String result = agendaServiceImpl.updateEmployee(List.of(requestUpdate, requestDelete));
        assertEquals("ok", result);
        verify(deviceRepository, times(1)).update(anyList());
        verify(employeeRepository, times(1)).update(anyList());
        verify(employeeRepository, times(1)).delete(anyList());
        verify(deviceRepository, times(1)).save(anyList());
    }

    @Test
    void testUpdateEmployee_Failure() {
        EmployeeRequest request = new EmployeeRequest();
        request.setId(1);
        request.setName("Test");
        request.setDeleteEmployee(false);
        request.setDeleteAssignedDevice(false);
        when(employeeMapper.toDto(request)).thenReturn(dummyEmployeeDto);
        when(deviceRepository.update(anyList())).thenReturn(0);
        when(employeeRepository.update(anyList())).thenReturn(0);
        when(employeeRepository.delete(anyList())).thenReturn(0);
        when(deviceRepository.save(anyList())).thenReturn(0);
        String result = agendaServiceImpl.updateEmployee(List.of(request));
        assertEquals("mal", result);
    }

    @Test
    void testUpdateEmployee_StatusException() {
        EmployeeRequest request = new EmployeeRequest();
        request.setId(1);
        request.setName("Test");
        request.setDeleteEmployee(false);
        request.setDeleteAssignedDevice(true);
        when(deviceRepository.getAssignation(request.getId())).thenThrow(new RuntimeException("Error asignación"));
        StatusException ex = assertThrows(StatusException.class, () -> agendaServiceImpl.updateEmployee(List.of(request)));
        assertTrue(ex.getMessage().contains("Error"));
    }
}
