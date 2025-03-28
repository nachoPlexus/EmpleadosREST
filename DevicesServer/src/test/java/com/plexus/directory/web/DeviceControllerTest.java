package com.plexus.directory.web;

import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.domain.error.NoContentException;
import com.plexus.directory.facade.DeviceFacade;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceControllerTest {

    @Mock
    private DeviceFacade facade;

    @InjectMocks
    private DeviceController deviceController;

    private DeviceDto deviceDto;
    private List<DeviceDto> devices;

    //paginas
    private final int resolvedPage = 0;
    private final int resolvedSize = 10;

    @BeforeEach
    void setUp() {
        deviceDto = new DeviceDto(1, "SN123456789", "Apple", "iPhone X", "iOS", 999);
        DeviceDto deviceDto2 = new DeviceDto(2, "SN987654321", "Samsung", "Galaxy S21", "Android", 888);
        DeviceDto deviceDto3 = new DeviceDto(3, "SN11122233344", "Xiaomi", "Mi 11", "Android", 777);
        devices = List.of(deviceDto, deviceDto2, deviceDto3);
    }

    // Endpoints individuales

    @Test
    void testGetDevices_Success() {
        when(facade.getDevicesPaged(resolvedPage, resolvedSize))
                .thenReturn(ResponseEntity.ok(new DevicePageResponse(devices, devices.size(), 1)));

        ResponseEntity<DevicePageResponse> response = deviceController.getDevices(1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getTotalDevices());
        verify(facade, times(1)).getDevicesPaged(resolvedPage, resolvedSize);
    }

    @Test
    void testGetDeviceById_Success() {
        when(facade.getDeviceById(1))
                .thenReturn(ResponseEntity.ok(deviceDto));

        ResponseEntity<DeviceDto> response = deviceController.getDeviceById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Apple", response.getBody().getBrand());
        verify(facade, times(1)).getDeviceById(1);
    }

    @Test
    void testGetDevicesByBrand_Success() {
        when(facade.getDevicesByBrand("Apple", resolvedPage, resolvedSize))
                .thenReturn(ResponseEntity.ok(new DevicePageResponse(devices, devices.size(), 1)));

        ResponseEntity<DevicePageResponse> response = deviceController.getDevicesByBrand("Apple", 1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(facade, times(1)).getDevicesByBrand("Apple", resolvedPage, resolvedSize);
    }

    @Test
    void testGetDeviceById_NotFound() {
        when(facade.getDeviceById(99))
                .thenThrow(new NoContentException("Dispositivo no encontrado"));

        Exception exception = assertThrows(NoContentException.class, () ->
                deviceController.getDeviceById(99)
        );

        assertEquals("Dispositivo no encontrado", exception.getMessage());
        verify(facade, times(1)).getDeviceById(99);
    }

    @Test
    void testGetDevicesByBrand_EmptyResponse() {
        when(facade.getDevicesByBrand("Unknown", resolvedPage, resolvedSize))
                .thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<DevicePageResponse> response = deviceController.getDevicesByBrand("Unknown", 1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(facade, times(1)).getDevicesByBrand("Unknown", resolvedPage, resolvedSize);
    }

    @Test
    void testCreateDevice_Success() {
        when(facade.createDevice(deviceDto))
                .thenReturn(ResponseEntity.ok("Dispositivo creado correctamente"));

        ResponseEntity<String> response = deviceController.createDevice(deviceDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dispositivo creado correctamente", response.getBody());
        verify(facade, times(1)).createDevice(deviceDto);
    }

    @Test
    void testUpdateDevice_Success() {
        when(facade.updateDevice(deviceDto))
                .thenReturn(ResponseEntity.ok("Dispositivo actualizado correctamente"));

        ResponseEntity<String> response = deviceController.updateDevice(deviceDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dispositivo actualizado correctamente", response.getBody());
        verify(facade, times(1)).updateDevice(deviceDto);
    }

    @Test
    void testDeleteDevice_Success() {
        when(facade.deleteDevice(1))
                .thenReturn(ResponseEntity.ok("Dispositivo eliminado correctamente"));

        ResponseEntity<String> response = deviceController.deleteDevice(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dispositivo eliminado correctamente", response.getBody());
        verify(facade, times(1)).deleteDevice(1);
    }

    @Test
    void testDeleteDevice_NotFound() {
        when(facade.deleteDevice(99))
                .thenThrow(new NoContentException("Dispositivo no encontrado"));

        Exception exception = assertThrows(NoContentException.class, () ->
                deviceController.deleteDevice(99)
        );

        assertEquals("Dispositivo no encontrado", exception.getMessage());
        verify(facade, times(1)).deleteDevice(99);
    }

    // Acciones masivas

    @Test
    void testCreateDevices_Success() {
        when(facade.createDevices(devices))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Devices creados bien"));

        ResponseEntity<String> response = deviceController.createDevices(devices);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Devices creados bien", response.getBody());
        verify(facade, times(1)).createDevices(devices);
    }

    @Test
    void testUpdateDevices_Success() {
        when(facade.updateDevice(devices))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED)
                        .body( "Todos los devices actualizados bien"));

        ResponseEntity<String> response = deviceController.updateDevices(devices);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Hurra!"));
        verify(facade, times(1)).updateDevice(devices);
    }

    @Test
    void testDeleteDevices_Success() {
        when(facade.deleteDevices(devices))
                .thenReturn(ResponseEntity.ok("Devices borrados bien"));

        ResponseEntity<String> response = deviceController.deleteDevices(devices);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Devices borrados bien", response.getBody());
        verify(facade, times(1)).deleteDevices(devices);
    }
}
