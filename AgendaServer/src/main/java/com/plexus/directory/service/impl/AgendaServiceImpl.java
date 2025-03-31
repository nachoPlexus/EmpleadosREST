package com.plexus.directory.service.impl;

import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.domain.mapper.EmployeeMapper;
import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.service.AgendaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService {
    private final EmployeeRepository employeeRepository;
    private final DeviceRepository deviceRepository;
    private final EmployeeMapper employeeMapper;
    private final DeviceMapper deviceMapper;

    public AgendaServiceImpl(EmployeeRepository employeeRepository, DeviceRepository deviceRepository, EmployeeMapper employeeMapper, DeviceMapper deviceMapper) {
        this.employeeRepository = employeeRepository;
        this.deviceRepository = deviceRepository;
        this.employeeMapper = employeeMapper;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public EmployeePageResponse getEmployeesPaged(int page, int size) {
        EmployeePageResponse employees = employeeMapper.toResponse(employeeRepository.getAll(page, size));
        employees.getEmployees().forEach(employeeResponse ->
                employeeResponse.setAssignedDevice(deviceMapper.toResponse(deviceRepository.getAssignation(employeeResponse.getEmployeeId()))));
        return employees;
    }

    @Override
    public EmployeePageResponse getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize) {
        EmployeePageResponse employees = employeeMapper.toResponse(employeeRepository.getByEmployeeName(employeeName, resolvedPage, resolvedSize));
        employees.getEmployees().forEach(employeeResponse ->
                employeeResponse.setAssignedDevice(deviceMapper.toResponse(deviceRepository.getAssignation(employeeResponse.getEmployeeId()))));
        return employees;
    }

    @Override
    public EmployeePageResponse getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize) {
        EmployeePageResponse employees = employeeMapper.toResponse(employeeRepository.getByEmployeeSurname(employeeSurname, resolvedPage, resolvedSize));
        employees.getEmployees().forEach(employeeResponse ->
                employeeResponse.setAssignedDevice(deviceMapper.toResponse(deviceRepository.getAssignation(employeeResponse.getEmployeeId()))));
        return employees;
    }

    @Override
    public EmployeeResponse getEmployeeById(int employeeId) {
        EmployeeResponse employee = employeeMapper.toResponse(employeeRepository.getEmployeeById(employeeId));
        employee.setAssignedDevice(deviceMapper.toResponse(deviceRepository.getAssignation(employee.getEmployeeId())));
        return employee;
    }

    @Override
    @Transactional
    public String createEmployee(List<EmployeeRequest> employeeRequests) {
        List<EmployeeDto> employeesToADD =new ArrayList<>();
        List<DeviceDto> devicesToAdd = new ArrayList<>();
        employeeRequests.forEach(em->{
            employeesToADD.add(employeeMapper.toDto(em));
            if (em.getAssignedDevice()!=null){
                devicesToAdd.addFirst(deviceMapper.toDto(em.getAssignedDevice()));
            }
        });
        employeeRepository.save(employeesToADD);
        deviceRepository.save(devicesToAdd);
        return "ok";
    }

    @Override
    public String updateEmployee(List<EmployeeRequest> employeeRequests) {
        List<EmployeeDto> employeesToUpdate = new ArrayList<>();
        List<DeviceDto>

        return "ok";
    }
}