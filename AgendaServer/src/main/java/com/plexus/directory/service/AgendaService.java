package com.plexus.directory.service;

import com.plexus.directory.domain.Employee;

import java.util.List;

public interface AgendaService {
    List<Employee> getAll(int page, int size);

    List<Employee> getByEmployeeName(String deviceName, int resolvedPage, int resolvedSize);
    List<Employee> getByEmployeeSurname(String deviceSurname, int resolvedPage, int resolvedSize);
    List<Employee> getByEmployeeId(String dev, int resolvedPage, int resolvedSize);

    int save(List<Employee> devices);

    int update(List<Employee> devices);
}
