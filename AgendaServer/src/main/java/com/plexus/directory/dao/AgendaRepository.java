package com.plexus.directory.dao;

import com.plexus.directory.domain.model.EmployeeDto;

import java.util.List;

public interface AgendaRepository {
    List<EmployeeDto> getAll(int page, int size);

    List<EmployeeDto> getByEmployeeName(String deviceName, int resolvedPage, int resolvedSize);
    List<EmployeeDto> getByEmployeeSurname(String deviceSurname, int resolvedPage, int resolvedSize);
    List<EmployeeDto> getByEmployeeId(String dev, int resolvedPage, int resolvedSize);

    int save(List<EmployeeDto> devices);

    int update(List<EmployeeDto> devices);

}
