package com.plexus.directory.service.impl;

import com.plexus.directory.dao.AgendaRepository;
import com.plexus.directory.domain.Device;
import com.plexus.directory.service.AgendaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("versionBase")
public class AgendaServiceImpl implements AgendaService {
    private final AgendaRepository dao;

    public AgendaServiceImpl(AgendaRepository dao) {
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

    public Device getAssignatedDevicesAsync() {
        return null;
    }
}
