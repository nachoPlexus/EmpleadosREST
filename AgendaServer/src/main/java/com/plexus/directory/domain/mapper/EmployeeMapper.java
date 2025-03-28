package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.model.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "mailPlexus", source = "mailPlexus")
    @Mapping(target = "mailClient", source = "mailClient")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "phoneSerialNumber", source = "phoneSerialNumber")
    EmployeeDto toDto(EmployeeDto responseEmloyee);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "mailPlexus", source = "mailPlexus")
    @Mapping(target = "mailClient", source = "mailClient")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "phoneSerialNumber", source = "phoneSerialNumber")
    EmployeeDto toEntity(EmployeeDto requestEmployee);

}
