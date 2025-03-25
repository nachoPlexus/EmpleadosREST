package com.plexus.directory.service.impl;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("versionBase")
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository dao;

    public EmployeeServiceImpl(EmployeeRepository dao) {
        this.dao = dao;
    }

    public List<Employee> getAll(int page, int size) {
        return dao.getAll(page, size);
    }

    public int getTotalEmployees() {
        return dao.countAll();
    }


    public Employee getById(int employeeId) {
        return dao.get(employeeId);
    }

    @Override
    public List<Employee> getByName(String employeeName,int resolvedPage, int resolvedSize) {
        return dao.get(employeeName, resolvedPage,resolvedSize);
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