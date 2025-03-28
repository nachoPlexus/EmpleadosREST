package com.plexus.directory.dao;

import com.plexus.directory.domain.model.EmployeeDto;

import java.util.List;

public interface EmployeeRepository {
    List<EmployeeDto> getAll(int page, int size);

    List<EmployeeDto> getByEmployeeName(String deviceName, int page, int size);

    List<EmployeeDto> getByEmployeeSurname(String deviceSurname, int page, int size);

    List<EmployeeDto> getByEmployeeId(String dev, int page, int size);

    int save(List<EmployeeDto> devices);

    int update(List<EmployeeDto> devices);
}
