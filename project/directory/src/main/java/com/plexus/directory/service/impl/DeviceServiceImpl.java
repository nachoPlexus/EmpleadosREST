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
    public List<Device> getAll() {
        return dao.getAll();
    }

    @Override
    public Device getById(int deviceId) {
        return dao.get(deviceId);
    }

    @Override
    public List<Device> getByBrand(String brand) {
        return dao.get(brand);
    }

    @Override
    public int save(Device device) {
        return dao.save(device);
    }

    @Override
    public int update(Device device) {
        return dao.update(device);
    }

    @Override
    public boolean delete(Device device) {
        return dao.delete(device);
    }
}
