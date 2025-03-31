package com.plexus.directory.dao;


import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.DevicePageDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("versionBase")
public interface DeviceRepository {
    DevicePageDto getAll(int page, int size);

    DeviceDto get(int id);

    DeviceDto getAssignation(int assignedEmployeeId);

    DevicePageDto get(String brand, int page, int size);

    int save(List<DeviceDto> devices);

    int update(List<DeviceDto> devices);

    int delete(List<DeviceDto> devices);
}
