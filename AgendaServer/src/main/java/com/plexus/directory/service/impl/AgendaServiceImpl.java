package com.plexus.directory.service.impl;

import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.service.AgendaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("versionBase")
public class AgendaServiceImpl implements AgendaService {

    @Override
    public List<EmployeeDto> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeName(String deviceName, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeSurname(String deviceSurname, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeId(String dev, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public int save(List<EmployeeDto> devices) {
        return 0;
    }

    @Override
    public int update(List<EmployeeDto> devices) {
        return 0;
    }
}
