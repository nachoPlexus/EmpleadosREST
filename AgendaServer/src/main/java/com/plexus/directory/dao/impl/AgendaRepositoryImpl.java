package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.AgendaRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.domain.model.EmployeeDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("versionBase")
public class AgendaRepositoryImpl extends GenericRepository implements AgendaRepository{

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
