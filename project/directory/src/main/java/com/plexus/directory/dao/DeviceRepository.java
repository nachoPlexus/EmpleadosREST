package com.plexus.directory.dao;

import com.plexus.directory.domain.Device;

import java.util.List;

public interface DeviceRepository {
    List<Device> getAll();
    Device get(int deviceId);
    List<Device> get(String brand);

    int save(Device device);
    int update(Device device);
    boolean delete(Device device);
}
