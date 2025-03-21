package com.plexus.directory.domain.mapper.trash;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperUnused {

    // de DTO a Entity
    public Employee toEntity(EmployeeDto dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setMailPlexus(dto.getMailPlexus());
        employee.setMailClient(dto.getMailClient());
        employee.setClientId(dto.getClientId());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setPhoneSerialNumber(dto.getPhoneSerialNumber());

        return employee;
    }

    // de Entity a DTO
    public EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setMailPlexus(employee.getMailPlexus());
        dto.setMailClient(employee.getMailClient());
        dto.setClientId(employee.getClientId());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setPhoneSerialNumber(employee.getPhoneSerialNumber());

        return dto;
    }
}
