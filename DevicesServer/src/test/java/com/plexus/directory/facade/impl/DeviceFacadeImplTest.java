package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.service.impl.DeviceServiceAsync;
import com.plexus.directory.service.impl.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceFacadeImplTest {
    //todo hacer test para acciones masivas

    @Mock
    private DeviceServiceImpl deviceService;

    @Mock
    private DeviceMapper deviceMapper;

    @Mock
    private DeviceServiceAsync asyncService;

    @InjectMocks
    private DeviceFacadeImpl deviceFacade;

    private Device device;
    private DeviceDto deviceDto;
    private List<Device> deviceList;
    private List<DeviceDto> deviceDtoList;

    @BeforeEach
    void setUp() {
        device = new Device(1, "Samsung", "SN123456789", "Galaxy S21", "Android", 101);
        deviceDto = new DeviceDto(1, "SN123456789", "Samsung", "Galaxy S21", "Android", 101);

        Device device2 = new Device(2, "Apple", "SN987654321", "iPhone 13", "iOS", 102);
        Device device3 = new Device(3, "Xiaomi", "SN11122233344", "Mi 11", "Android", 103);
        deviceList = List.of(device, device2, device3);

        DeviceDto deviceDto2 = new DeviceDto(2, "SN987654321", "Apple", "iPhone 13", "iOS", 102);
        DeviceDto deviceDto3 = new DeviceDto(3, "SN11122233344", "Xiaomi", "Mi 11", "Android", 103);
        deviceDtoList = List.of(deviceDto, deviceDto2, deviceDto3);
    }

    @Test
    void testGetDevicesPaged_Success() {
        when(deviceService.getAll(1, 10)).thenReturn(deviceList);
        when(deviceMapper.toDto(any())).thenReturn(deviceDto);
        when(asyncService.getDevicesCountAsync()).thenReturn(CompletableFuture.completedFuture(deviceList.size()));

        ResponseEntity<DevicePageResponse> response = deviceFacade.getDevicesPaged(1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList.size(), response.getBody().getDevices().size());
        verify(deviceService, times(1)).getAll(1, 10);
        verify(deviceMapper, times(deviceList.size())).toDto(any());
    }

    @Test
    void testGetDeviceById_Success() {
        when(deviceService.getById(1)).thenReturn(device);
        when(deviceMapper.toDto(device)).thenReturn(deviceDto);

        ResponseEntity<DeviceDto> response = deviceFacade.getDeviceById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Samsung", response.getBody().getBrand());
        verify(deviceService, times(1)).getById(1);
        verify(deviceMapper, times(1)).toDto(device);
    }

    @Test
    void testGetDevicesByBrand_Success() {
        when(deviceService.getByBrand("Samsung", 1, 10)).thenReturn(deviceList);
        when(deviceMapper.toDto(any())).thenReturn(deviceDto);

        ResponseEntity<DevicePageResponse> response = deviceFacade.getDevicesByBrand("Samsung", 1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceList.size(), response.getBody().getDevices().size());
        verify(deviceService, times(1)).getByBrand("Samsung", 1, 10);
        verify(deviceMapper, times(deviceList.size())).toDto(any());
    }

    @Test
    void testCreateDevice_Success() {
        when(deviceMapper.toEntity(deviceDto)).thenReturn(device);
        when(deviceService.save(device)).thenReturn(1);

        ResponseEntity<String> response = deviceFacade.createDevice(deviceDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Device creado bien", response.getBody());
        verify(deviceService, times(1)).save(device);
    }

    @Test
    void testCreateDevice_Failure() {
        when(deviceMapper.toEntity(deviceDto)).thenReturn(device);
        when(deviceService.save(device)).thenReturn(0);

        ResponseEntity<String> response = deviceFacade.createDevice(deviceDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("No se va a ver esto nunca, antes salta exception", response.getBody());
    }

    @Test
    void testUpdateDevice_Success() {
        when(deviceMapper.toEntity(deviceDto)).thenReturn(device);
        when(deviceService.update(device)).thenReturn(1);

        ResponseEntity<String> response = deviceFacade.updateDevice(deviceDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device actualizado bien", response.getBody());
        verify(deviceService, times(1)).update(device);
    }

    @Test
    void testUpdateDevice_Failure() {
        when(deviceMapper.toEntity(deviceDto)).thenReturn(device);
        when(deviceService.update(device)).thenReturn(0);

        ResponseEntity<String> response = deviceFacade.updateDevice(deviceDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("No se va a ver esto nunca, antes salta exception", response.getBody());
    }

    @Test
    void testDeleteDevice_Success() {
        when(deviceService.getById(1)).thenReturn(device);
        when(deviceService.delete(device)).thenReturn(true);

        ResponseEntity<String> response = deviceFacade.deleteDevice(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Device eliminado bien", response.getBody());
        verify(deviceService, times(1)).delete(device);
    }

    @Test
    void testDeleteDevice_Failure() {
        when(deviceService.getById(1)).thenReturn(device);
        when(deviceService.delete(device)).thenReturn(false);

        ResponseEntity<String> response = deviceFacade.deleteDevice(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("No se va a ver esto nunca, antes salta exception", response.getBody());
    }

    // Acciones masivas

    @Test
    void testCreateDevices_Success() {
        when(deviceService.save(any(List.class))).thenReturn(deviceDtoList.size() + 1);

        ResponseEntity<String> response = deviceFacade.createDevices(deviceDtoList);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Devices creados bien"));
        verify(deviceService, times(1)).save(any(List.class));
    }

    @Test
    void testUpdateDevices_Success() {
        when(deviceService.update(any(List.class))).thenReturn(deviceDtoList.size());

        ResponseEntity<Map<String, Object>> response = deviceFacade.updateDevice(deviceDtoList);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("Hurra!"));
        verify(deviceService, times(1)).update(any(List.class));
    }

    @Test
    void testDeleteDevices_Success() {
        when(deviceService.delete(any(List.class))).thenReturn(deviceDtoList.size());

        ResponseEntity<String> response = deviceFacade.deleteDevices(deviceDtoList);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Devices borrados bien", response.getBody());
        verify(deviceService, times(1)).delete(any(List.class));
    }
}
