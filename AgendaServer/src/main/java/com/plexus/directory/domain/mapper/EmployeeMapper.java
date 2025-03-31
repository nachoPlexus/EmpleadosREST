package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.DeviceResponse;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(EmployeeRequest request);

    @Mapping(target = "employeeId",source = "id")
    @Mapping(target = "employeeName",source = "name")
    @Mapping(target = "employeeSurname",source = "surname")
    @Mapping(target = "plexusMail",source = "mailPlexus")
    @Mapping(target = "clientMail",source = "mailClient")
    @Mapping(target = "phoneNumber",source = "phoneNumber")
    EmployeeResponse toResponse(EmployeeDto dto);


    @Mapping(target = "pageNumber", source = "pageNumber")
    @Mapping(target = "totalEmployees", source = "totalEmployees")
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeePageDtoToEmployeePageResponse")
    EmployeePageResponse toResponse(EmployeePageDto dto);

    @Named("employeePageDtoToEmployeePageResponse")
    default List<EmployeeResponse> mapEmployees(List<EmployeeDto> employeeDtos) {
        return employeeDtos.stream()
                .map(this::dtoEmployeeToResponseEmployee)
                .toList();
    }

    private EmployeeResponse dtoEmployeeToResponseEmployee(EmployeeDto deviceResponse) {
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(deviceResponse.getId());
        response.setEmployeeName(deviceResponse.getName());
        response.setEmployeeSurname(deviceResponse.getSurname());
        response.setPlexusMail(deviceResponse.getMailPlexus());
        response.setClientMail(deviceResponse.getMailClient());
        response.setPhoneNumber(deviceResponse.getPhoneNumber()==null
                                                                ?-1
                                                                :deviceResponse.getPhoneNumber());
        response.setAssignedDevice(new DeviceResponse());
        return response;
    }

}
