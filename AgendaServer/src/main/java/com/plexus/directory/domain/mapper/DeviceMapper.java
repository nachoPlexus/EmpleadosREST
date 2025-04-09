package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.request.DeviceRequest;
import com.plexus.directory.domain.model.response.DeviceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "os", source = "operatingSystem")
    DeviceDto toDto(DeviceRequest request);

    @Mapping(target = "deviceId", source = "id")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "operatingSystem", source = "os")
    DeviceResponse toResponse(DeviceDto databaseDevice);

}
