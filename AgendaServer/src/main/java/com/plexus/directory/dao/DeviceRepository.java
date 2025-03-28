package com.plexus.directory.dao;


import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.DevicePageDto;

import java.util.List;

public interface DeviceRepository {
    DevicePageDto getAll(int page, int size);

    DeviceDto get(int id);

    DevicePageDto get(String brand, int page, int size);

    int save(List<DeviceDto> devices);

    int update(List<DeviceDto> devices);

    int delete(List<DeviceDto> devices);
}
