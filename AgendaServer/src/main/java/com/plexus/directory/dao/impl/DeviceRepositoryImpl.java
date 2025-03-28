package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.dao.retrofit.llamadas.DevicesApi;
import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.DevicePageDto;

import java.util.List;

public class DeviceRepositoryImpl extends GenericRepository implements DeviceRepository {
    private final DevicesApi api;

    public DeviceRepositoryImpl(DevicesApi api) {
        this.api = api;
    }


    @Override
    public DevicePageDto getAll(int page, int size) {
        return safeApiCall(api.getDevices(page, size));
    }

    @Override
    public DeviceDto get(int id) {
        return safeApiCall(api.getDeviceById(id));
    }

    @Override
    public DevicePageDto get(String brand, int page, int size) {
        return safeApiCall(api.getDevicesByBrand(brand, page, size));
    }

    @Override
    public int save(List<DeviceDto> devices) {
        return safeApiCall(api.createDevices(devices))
                .equals("Devices creados bien") ? -1 : 1 ;
    }

    @Override
    public int update(List<DeviceDto> devices) {
        return safeApiCall(api.updateDevices(devices))
                .equals("Todos los devices actualizados bien") ? -1 : 1 ;
    }

    @Override
    public int delete(List<DeviceDto> devices) {
        return safeApiCall(api.deleteDevices(devices))
                .equals("Devices borrados bien") ? -1 : 1 ;
    }
}
