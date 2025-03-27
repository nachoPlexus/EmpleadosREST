package com.plexus.directory.service;

import com.plexus.directory.domain.Device;

import java.util.List;

public interface DeviceService {
    List<Device> getAll(int page, int size);

    Device getById(int deviceId);

    List<Device> getByBrand(String brand, int resolvedPage, int resolvedSize);

    int countAll();

    int save(List<Device> devices);

    int update(List<Device> devices);

    int delete(List<Device> devices);
}
