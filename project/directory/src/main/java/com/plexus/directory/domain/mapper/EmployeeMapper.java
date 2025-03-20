package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee response);

    Employee toEntity(EmployeeDto request);

}
