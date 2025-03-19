package com.plexus.directory.dao;

import com.plexus.directory.domain.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll();
    Employee get(int employeeId);
    List<Employee> get(String employeeName);

    int save(Employee employee);
    int update(Employee employee);
    boolean delete(Employee employee);
}
