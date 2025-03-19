package com.plexus.directory.service;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.Employee;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository dao;

    public EmployeeService(EmployeeRepository dao) {
        this.dao = dao;
    }

    public List<Employee> getAll() {
        return dao.getAll();
    }

    public Employee getById(int employeeId) {
        Employee employee = dao.get(employeeId);
        return employee;
    }

    public List<Employee> getByName(String employeeName) {
        return dao.get(employeeName);
    }

    @Transactional
    public int save(Employee employee) {
        return dao.save(employee);
    }

    @Transactional
    public int update(Employee employee) {
        return dao.update(employee);
    }

    public boolean delete(Employee employee) {
        return dao.delete(employee);
    }
}
