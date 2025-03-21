package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class DeviceFacadeImplTest {

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
    void getDevicesPaged() {
    }

    void getDeviceById() {
    }

    @Test
    void getDevicesByBrand() {
    }

    @Test
    void createDevice() {
    }

    @Test
    void updateDevice() {
    }

    @Test
    void deleteDevice() {
    }
}