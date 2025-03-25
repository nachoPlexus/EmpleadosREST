package com.plexus.directory.dao;

import com.plexus.directory.domain.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll(int page, int size);
    Employee get(int employeeId);
    List<Employee> get(String employeeName,int resolvedPage, int resolvedSize);
    int save(Employee employee);
    int update(Employee employee);
    boolean delete(Employee employee);

    int countAll();
}
