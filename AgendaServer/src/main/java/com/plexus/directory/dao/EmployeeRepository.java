package com.plexus.directory.dao;

import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("versionBase")
public interface EmployeeRepository {
    EmployeePageDto getAll(int page, int size);

    EmployeePageDto getByEmployeeName(String deviceName, int page, int size);

    EmployeePageDto getByEmployeeSurname(String deviceSurname, int page, int size);

    EmployeeDto getEmployeeById(int id);

    List<Integer> save(List<EmployeeDto> devices);

    int update(List<EmployeeDto> devices);

    int delete(List<EmployeeDto> employees);
}
