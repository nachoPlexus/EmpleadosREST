package com.plexus.directory.dao;

import com.plexus.directory.domain.Device;

import java.util.List;

public interface DeviceRepository {
    List<Device> getAll(int page, int size);

    Device get(int deviceId);

    List<Device> get(String brand, int resolvedPage, int resolvedSize);

    int countAll();

    int save(List<Device> devices);

    int update(List<Device> devices);

    int delete(List<Device> device);

}
