package com.plexus.directory.service.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.mapper.DeviceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    @Mock
    private DeviceMapper mapper;

    private Device device;
    private List<Device> devices;

    @BeforeEach
    void setUp() {
        device = new Device(1,"ABC12345678", "Apple", "iPhone X", "iOS",999);
        Device device2 = new Device(2, "DEF12345678", "Samsung", "Galaxy S21", "Android", 888);
        Device device3 = new Device(3, "GHI12345678", "Xiaomi", "Mi 11", "Android", 777);
        devices = List.of(device, device2, device3);
    }

    @Test
    void testGetAll_Success() {
        when(deviceRepository.getAll()).thenReturn(devices);

        List<Device> result = deviceServiceImpl.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(deviceRepository, times(1)).getAll();
    }

    @Test
    void testGetAll_DataBaseError() {
        when(deviceRepository.getAll()).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> deviceServiceImpl.getAll());
        assertTrue(ex.getMessage().contains("DB error"));
        verify(deviceRepository, times(1)).getAll();
    }

    @Test
    void testGetById_Success() {
        when(deviceRepository.get(1)).thenReturn(device);

        Device result = deviceServiceImpl.getById(1);

        assertNotNull(result);
        assertEquals("Apple", result.getBrand());
        verify(deviceRepository, times(1)).get(1);
    }

    @Test
    void testGetById_DataBaseError() {
        when(deviceRepository.get(1)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> deviceServiceImpl.getById(1));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(deviceRepository, times(1)).get(1);
    }

    @Test
    void testGetByBrand_Success() {
        when(deviceRepository.get("Samsung")).thenReturn(devices);

        List<Device> result = deviceServiceImpl.getByBrand("Samsung");

        assertNotNull(result);
        assertEquals("Samsung", result.get(1).getBrand());
        verify(deviceRepository, times(1)).get("Samsung");
    }

    @Test
    void testGetByBrand_DataBaseError() {
        when(deviceRepository.get("Samsung")).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> deviceServiceImpl.getByBrand("Samsung"));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(deviceRepository, times(1)).get("Samsung");
    }

    @Test
    void testSaveDevice_Success() {
        when(deviceRepository.save(device)).thenReturn(1);

        int result = deviceServiceImpl.save(device);

        assertEquals(1, result);
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void testSaveDevice_DataBaseError() {
        when(deviceRepository.save(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deviceServiceImpl.save(device));
        assertTrue(exception.getMessage().contains("DB error"));
    }

    @Test
    void testUpdateDevice_Success() {
        when(deviceRepository.update(device)).thenReturn(1);

        int result = deviceServiceImpl.update(device);

        assertEquals(1, result);
        verify(deviceRepository, times(1)).update(device);
    }

    @Test
    void testUpdateDevice_DataBaseError() {
        when(deviceRepository.update(any())).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> deviceServiceImpl.update(device));
        assertTrue(ex.getMessage().contains("DB error"));
    }

    @Test
    void testDeleteDevice_Success() {
        when(deviceRepository.delete(device)).thenReturn(true);

        boolean result = deviceServiceImpl.delete(device);
        assertTrue(result);
        verify(deviceRepository, times(1)).delete(device);
    }

    @Test
    void testDeleteDevice_Failure() {
        when(deviceRepository.delete(device)).thenReturn(false);

        boolean result = deviceServiceImpl.delete(device);
        assertFalse(result);
        verify(deviceRepository, times(1)).delete(device);
    }
}