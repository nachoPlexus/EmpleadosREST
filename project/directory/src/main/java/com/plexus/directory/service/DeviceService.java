package com.plexus.directory.service;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.dto.DevicePageResponse;

import java.util.List;

public interface DeviceService {

    List<Device> getAll();
    Device getById(int deviceId);
    List<Device> getByBrand(String brand);
    int save(Device device);
    int update(Device device);
    boolean delete(Device device);
}
