package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.dao.retrofit.llamadas.DevicesApi;
import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.DevicePageDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("versionBase")
public class DeviceRepositoryImpl extends GenericRepository implements DeviceRepository {
    private final DevicesApi api;

    public DeviceRepositoryImpl(DevicesApi api) {
        this.api = api;
    }

    @Override
    public DevicePageDto getAll(int page, int size) {
        return safeApiCall(api.getDevices(page, size), DevicePageDto.class);
    }

    @Override
    public DeviceDto get(int id) {
        return safeApiCall(api.getDeviceById(id), DeviceDto.class);
    }

    @Override
    public DeviceDto getAssignation(int assignedEmployeeId) {
        return safeApiCall(api.getDeviceByEmployeeId(assignedEmployeeId),DeviceDto.class);
    }

    @Override
    public DevicePageDto get(String brand, int page, int size) {
        return safeApiCall(api.getDevicesByBrand(brand, page, size),DevicePageDto.class);
    }

    @Override
    public int save(List<DeviceDto> devices) {
        return safeApiCall(api.createDevices(devices),String.class)
                .equals("Devices creados bien") ? -1 : 1 ;
    }

    @Override
    public int update(List<DeviceDto> devices) {
        return safeApiCall(api.updateDevices(devices),String.class)
                .equals("Todos los devices actualizados bien") ? -1 : 1 ;
    }

    @Override
    public int delete(List<DeviceDto> devices) {
        return safeApiCall(api.deleteDevices(devices),String.class)
                .equals("Devices borrados bien") ? -1 : 1 ;
    }
}
