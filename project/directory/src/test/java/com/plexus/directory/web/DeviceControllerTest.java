package com.plexus.directory.web;

import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.NoContentException;
import com.plexus.directory.facade.DeviceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceFacade facade2;

    @Mock
    private DeviceFacade facade;

    @InjectMocks
    private DeviceController deviceController;



    private DeviceDto deviceDto;
    private List<DeviceDto> devices;

    @BeforeEach
    void setUp() {
        deviceDto = new DeviceDto();
        deviceDto.setId(1);
        deviceDto.setSerialNumber("ABC12345678");
        deviceDto.setBrand("Apple");
        deviceDto.setModel("iPhone X");
        deviceDto.setOs("iOS");
        deviceDto.setAssignedTo(999);

        DeviceDto device2 = new DeviceDto(2, "DEF12345678", "Samsung", "Galaxy S21", "Android", 888);
        DeviceDto device3 = new DeviceDto(3, "GHI12345678", "Xiaomi", "Mi 11", "Android", 777);
        devices = List.of(deviceDto, device2, device3);
    }

    @Test
    void getDevices() {
        when(facade.getDevicesPaged(1, 10))
                .thenReturn(ResponseEntity.ok(new DevicePageResponse(devices, devices.size(), 1)));

        ResponseEntity<DevicePageResponse> response = deviceController.getDevices(1, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getTotalDevices());
        verify(facade, times(1)).getDevicesPaged(1, 10);
    }

    @Test
    void getDeviceById() {
        when(facade.getDeviceById(1))
                .thenReturn(ResponseEntity.ok(deviceDto));

        ResponseEntity<DeviceDto> response = deviceController.getDeviceById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Apple", response.getBody().getBrand());
        verify(facade, times(1)).getDeviceById(1);
    }

    @Test
    void getDevicesByBrand() {
        when(facade.getDevicesByBrand("Apple"))
                .thenReturn(ResponseEntity.ok(new DevicePageResponse(devices, devices.size(), 1)));

        ResponseEntity<DevicePageResponse> response = deviceController.getDevicesByBrand("Apple");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(facade, times(1)).getDevicesByBrand("Apple");
    }

    @Test
    void getDeviceById_NotFound() {
        when(facade.getDeviceById(99))
                .thenThrow(new NoContentException("Dispositivo no encontrado"));

        Exception exception = assertThrows(NoContentException.class, () ->
                deviceController.getDeviceById(99)
        );

        assertEquals("Dispositivo no encontrado", exception.getMessage());
        verify(facade, times(1)).getDeviceById(99);
    }

    @Test
    void getDevicesByBrand_EmptyResponse() {
        when(facade.getDevicesByBrand("Unknown"))
                .thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<DevicePageResponse> response = deviceController.getDevicesByBrand("Unknown");

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(facade, times(1)).getDevicesByBrand("Unknown");
    }

    @Test
    void createDevice() {
        when(facade.createDevice(deviceDto))
                .thenReturn(ResponseEntity.ok("Dispositivo creado correctamente"));

        ResponseEntity<String> response = deviceController.createDevice(deviceDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dispositivo creado correctamente", response.getBody());
        verify(facade, times(1)).createDevice(deviceDto);
    }

    @Test
    void updateDevice() {
        when(facade.updateDevice(deviceDto))
                .thenReturn(ResponseEntity.ok("Dispositivo actualizado correctamente"));

        ResponseEntity<String> response = deviceController.updateDevice(deviceDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dispositivo actualizado correctamente", response.getBody());
        verify(facade, times(1)).updateDevice(deviceDto);
    }

    @Test
    void deleteDevice() {
        when(facade.deleteDevice(1))
                .thenReturn(ResponseEntity.ok("Dispositivo eliminado correctamente"));

        ResponseEntity<String> response = deviceController.deleteDevice(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Dispositivo eliminado correctamente", response.getBody());
        verify(facade, times(1)).deleteDevice(1);
    }

    @Test
    void deleteDevice_NotFound() {
        when(facade.deleteDevice(99))
                .thenThrow(new NoContentException("Dispositivo no encontrado"));

        Exception exception = assertThrows(NoContentException.class, () ->
                deviceController.deleteDevice(99)
        );

        assertEquals("Dispositivo no encontrado", exception.getMessage());
        verify(facade, times(1)).deleteDevice(99);
    }

    // Validaciones con Jakarta
    // DTO con datos malos malísimos
    @Test
    void createDevice_InvalidData() throws Exception {
        String invalidDeviceJson = """
            {
              "brand": "Nacho",
              "serialNumber": "",
              "os": "nacho.llorente2@plexus.com"
            }
        """;

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidDeviceJson))  // Aquí usamos .content() en lugar de .body()
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Your request was invalid :El serialNumber es obligatorio"));

        verify(facade2, never()).createDevice(any());
    }


    @Test
    void updateDevice_InvalidData() {
        DeviceDto invalidDeviceDto = new DeviceDto();
        invalidDeviceDto.setBrand(""); // brand vacío

        Exception exception = assertThrows(BadRequestException.class, () ->
                deviceController.updateDevice(invalidDeviceDto)
        );

        assertEquals("Your request was invalid :La marca es obligatoria", exception.getMessage());
        verify(facade, times(1)).updateDevice(invalidDeviceDto);
    }

    @Test
    void createDevice_ValidationError() {
        DeviceDto invalidDeviceDto = new DeviceDto();
        invalidDeviceDto.setSerialNumber(""); // Valor malo

        Exception exception = assertThrows(BadRequestException.class, () ->
                deviceController.createDevice(invalidDeviceDto)
        );

        assertEquals("Your request was invalid :El serialNumber es obligatorio", exception.getMessage());
        verify(facade, times(1)).createDevice(invalidDeviceDto);
    }
}
