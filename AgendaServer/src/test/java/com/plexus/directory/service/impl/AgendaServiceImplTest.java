package com.plexus.directory.service.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import com.plexus.directory.domain.model.request.DeviceRequest;
import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.DeviceResponse;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
    private AgendaServiceImpl agendaService;

    private EmployeeRequest validRequest;
    private EmployeeDto employeeDto;
    private DeviceDto deviceDto;
    private EmployeeResponse validResponse;

    @BeforeEach
    void setUp() {
        validRequest = new EmployeeRequest(
                1,
                "John",
                "Doe",
                "john.doe@plexus.es",
                "john.doe@client.es",
                "666777888",
                new DeviceRequest("SN123", "Brand", "Model", "OS"),
                false,
                false
        );
        validResponse = new EmployeeResponse(1, "John", "Doe", "mail", "clientmail", 666666666, null);
        employeeDto = new EmployeeDto(1, "John", "Doe", "john.doe@plexus.es", "john.doe@client.es", "123", 666666666, "dwad");
        deviceDto = new DeviceDto(1, "SN123", "Brand", "Model", "OS", 1);
    }

    @Test
    void getEmployeesPaged_Success() {
        when(employeeRepository.getAll(1, 10)).thenReturn(new EmployeePageDto(List.of(employeeDto), 1, 10));
        when(employeeMapper.toResponse(any(EmployeePageDto.class))).thenReturn(new EmployeePageResponse(
                Collections.singletonList(new EmployeeResponse()), 1, 1));
        when(deviceRepository.getAssignation(anyInt())).thenReturn(new DeviceDto());
        when(deviceMapper.toResponse(any(DeviceDto.class))).thenReturn(new DeviceResponse());

        EmployeePageResponse result = agendaService.getEmployeesPaged(1, 10);

        verify(employeeRepository).getAll(1, 10);
    }

    @Test
    void getEmployeeById_Success() {
        when(employeeRepository.getEmployeeById(1)).thenReturn(new EmployeeDto());
        when(employeeMapper.toResponse(any(EmployeeDto.class)))
                .thenReturn(validResponse);
        when(deviceRepository.getAssignation(1)).thenReturn(new DeviceDto());
        when(deviceMapper.toResponse(any())).thenReturn(new DeviceResponse());

        EmployeeResponse result = agendaService.getEmployeeById(1);

        assertNotNull(result);
        assertEquals("John", result.getEmployeeName());
    }

    @Test
    void createEmployee_Success() {
        when(employeeMapper.toDto(any())).thenReturn(employeeDto);
        when(deviceMapper.toDto(any())).thenReturn(deviceDto);
        when(employeeRepository.save(anyList())).thenReturn(1);
        when(deviceRepository.save(any())).thenReturn(1);

        String result = agendaService.createEmployee(Collections.singletonList(validRequest));

        assertEquals("ok", result);
        verify(employeeRepository).save(any());
        verify(deviceRepository).save(any());
    }

    @Test
    void updateEmployee_AllOperationsSuccess() {
        EmployeeRequest request = new EmployeeRequest(
                1, "John", "Doe", "mail", "mail2", "666666666", null, true, true);

        when(employeeMapper.toDto(any(EmployeeRequest.class))).thenReturn(employeeDto);
        when(deviceRepository.getAssignation(1)).thenReturn(deviceDto);
        when(deviceRepository.update(any())).thenReturn(1);
        when(employeeRepository.update(any())).thenReturn(1);
        when(employeeRepository.delete(any())).thenReturn(1);
        when(deviceRepository.save(any())).thenReturn(1);

        String result = agendaService.updateEmployee(Collections.singletonList(request));

        assertEquals("ok", result);
        verify(deviceRepository).update(any());
    }

    @Test
    void updateEmployee_UnlinkDeviceError() {
        EmployeeRequest request = new EmployeeRequest(
                1, "John", "Doe", "mail", "phone", "phone2", null, true, true);

        when(employeeMapper.toDto(any(EmployeeRequest.class))).thenReturn(employeeDto);
        when(deviceRepository.getAssignation(1)).thenThrow(new RuntimeException("DB error"));

        StatusException ex = assertThrows(StatusException.class,
                () -> agendaService.updateEmployee(Collections.singletonList(request)));

        assertTrue(ex.getDetails().containsKey("DeletingAssignedDeviceError"));
    }

    @Test
    void updateEmployee_PartialSuccess() {
        when(employeeMapper.toDto(any(EmployeeRequest.class))).thenReturn(employeeDto);
        when(deviceRepository.update(anyList())).thenReturn(0);
        when(employeeRepository.update(anyList())).thenReturn(1);
        when(employeeRepository.delete(anyList())).thenReturn(1);
        when(deviceRepository.save(anyList())).thenReturn(1);

        String result = agendaService.updateEmployee(Collections.singletonList(validRequest));

        assertEquals("mal", result);
    }

    @Test
    void getEmployeesByName_Success() {
        when(employeeRepository.getByEmployeeName("John", 1, 10))
                .thenReturn(new EmployeePageDto(List.of(employeeDto), 1, 1));

        when(employeeMapper.toResponse(any(EmployeePageDto.class)))
                .thenReturn(new EmployeePageResponse(Collections.singletonList(validResponse), 1, 1));

        when(deviceRepository.getAssignation(anyInt())).thenReturn(deviceDto);
        when(deviceMapper.toResponse(any(DeviceDto.class))).thenReturn(new DeviceResponse());

        EmployeePageResponse result = agendaService.getEmployeesByName("John", 1, 10);

        assertNotNull(result);
        assertEquals(1, result.getPageNumber());
        assertEquals(1, result.getEmployees().size());
        verify(employeeRepository).getByEmployeeName("John", 1, 10);
    }

    @Test
    void getEmployeesBySurname_Success() {
        when(employeeRepository.getByEmployeeSurname("Doe", 1, 10))
                .thenReturn(new EmployeePageDto(List.of(employeeDto), 1, 1));

        when(employeeMapper.toResponse(any(EmployeePageDto.class)))
                .thenReturn(new EmployeePageResponse(Collections.singletonList(validResponse), 1, 1));

        when(deviceRepository.getAssignation(anyInt())).thenReturn(deviceDto);
        when(deviceMapper.toResponse(any(DeviceDto.class))).thenReturn(new DeviceResponse());

        EmployeePageResponse result = agendaService.getEmployeesBySurname("Doe", 1, 10);

        assertNotNull(result);
        assertEquals(1, result.getPageNumber());
        assertEquals(1, result.getEmployees().size());
        verify(employeeRepository).getByEmployeeSurname("Doe", 1, 10);
    }


    @Test
    void createEmployee_DatabaseError() {
        when(employeeMapper.toDto(any(EmployeeRequest.class))).thenReturn(employeeDto);
        when(employeeRepository.save(anyList())).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> agendaService.createEmployee(Collections.singletonList(validRequest)));
    }
}