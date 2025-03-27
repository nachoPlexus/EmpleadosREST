package com.plexus.directory.service.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.domain.Device;
import com.plexus.directory.service.DeviceService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("versionBase")
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository dao;

    public DeviceServiceImpl(DeviceRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Device> getAll(int page, int size) {
        return dao.getAll(page,size);
    }

    @Override
    public Device getById(int deviceId) {
        return dao.get(deviceId);
    }

    @Override
    public List<Device> getByBrand(String brand, int resolvedPage, int resolvedSize) {
        return dao.get(brand,resolvedPage,resolvedSize);
    }

    @Override
    public int countAll() {
        return dao.countAll();
    }

    @Override
    public int save(List<Device> devices) {
        return dao.save(devices);
    }

    @Override
    public int update(List<Device> devices) {
        return dao.update(devices);
    }

    @Override
    public int delete(List<Device> devices) {
        return dao.delete(devices);
    }

}
