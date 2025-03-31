package com.plexus.directory.service;

import com.plexus.directory.domain.Employee;

import java.util.Collection;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAll(int page, int size);

    int getTotalEmployees();

    Employee getById(int employeeId);

    List<Employee> getByName(String employeeName,int page, int size);

    int save(Employee employee);

    int update(Employee employee);

    boolean delete(Employee employee);

    int updateAllSurnamesToCamelCase();

    List<Employee> getBySurname(String surnameValue, int page, int size);
}
