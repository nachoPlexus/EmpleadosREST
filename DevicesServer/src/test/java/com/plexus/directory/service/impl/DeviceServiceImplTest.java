package com.plexus.directory.service.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.domain.Device;
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
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    private Device device;
    private List<Device> devices;

    //paginas
    private final int page = 1;
    private final int size = 200;

    @BeforeEach
    void setUp() {
        device = new Device(1, "SN123456789", "Apple", "iPhone X", "iOS", 999);
        Device device2 = new Device(2, "SN987654321", "Samsung", "Galaxy S21", "Android", 888);
        Device device3 = new Device(3, "SN11122233344", "Xiaomi", "Mi 11", "Android", 777);
        Device device4 = new Device(2, "SN987654321", "Samsung", "Galaxy S22", "Android", 999);
        devices = List.of(device, device2, device3);
    }

    @Test
    void testGetAll_Success() {
        when(deviceRepository.getAll(page, size)).thenReturn(devices);

        List<Device> result = deviceServiceImpl.getAll(page, size);

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(deviceRepository, times(1)).getAll(page, size);
    }

    @Test
    void testGetAll_DataBaseError() {
        when(deviceRepository.getAll(page, size)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> deviceServiceImpl.getAll(page, size));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(deviceRepository, times(1)).getAll(page, size);
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
        when(deviceRepository.get("Samsung", page, size)).thenReturn(
                devices.stream().filter(device1 -> device1.getBrand().equalsIgnoreCase("Samsung")).toList());

        List<Device> result = deviceServiceImpl.getByBrand("Samsung", page, size);
        int randomIndex = new Random().nextInt(result.size());

        assertNotNull(result);
        // Verificamos que alguno de los dispositivos tenga la marca "Samsung"
        assertEquals("Samsung", result.get(randomIndex).getBrand());
        verify(deviceRepository, times(1)).get("Samsung", page, size);
    }

    @Test
    void testGetByBrand_DataBaseError() {
        when(deviceRepository.get("Samsung", page, size)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> deviceServiceImpl.getByBrand("Samsung", page, size));
        assertTrue(ex.getMessage().contains("DB error"));
        verify(deviceRepository, times(1)).get("Samsung", page, size);
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
        when(deviceRepository.save((Device) any())).thenThrow(new RuntimeException("DB error"));

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
        when(deviceRepository.update((Device) any())).thenThrow(new RuntimeException("DB error"));

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
