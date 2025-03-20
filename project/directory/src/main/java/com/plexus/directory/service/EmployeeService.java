package com.plexus.directory.service;

import com.plexus.directory.domain.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getById(int employeeId);

    List<Employee> getByName(String employeeName);

    int save(Employee employee);

    int update(Employee employee);

    boolean delete(Employee employee);

}
