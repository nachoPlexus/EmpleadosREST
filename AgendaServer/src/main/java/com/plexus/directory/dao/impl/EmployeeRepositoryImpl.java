package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.domain.model.EmployeeDto;

import java.util.List;

public class EmployeeRepositoryImpl extends GenericRepository implements EmployeeRepository {
    @Override
    public List<EmployeeDto> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeName(String deviceName, int page, int size) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeSurname(String deviceSurname, int page, int size) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeId(String dev, int page, int size) {
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
