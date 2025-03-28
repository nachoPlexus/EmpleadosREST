package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.request.DeviceRequest;
import com.plexus.directory.domain.model.response.DeviceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    //TODO DE REQUIEST A DTO, DE DTO A RESPONSE

    DeviceDto toDto(DeviceRequest request);

    DeviceResponse toResponse(DeviceDto databaseDevice);
}
