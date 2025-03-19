package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    // de DTO a Entity
    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId() != null ? dto.getId() : -1);
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
    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDTO dto = new EmployeeDTO();
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
