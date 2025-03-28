package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.dto.DeviceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "os", source = "os")
    @Mapping(target = "assignedTo", source = "assignedTo")
    DeviceDto toDto(DeviceDto responseDeviceDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "os", source = "os")
    @Mapping(target = "assignedTo", source = "assignedTo")
    DeviceDto toEntity(DeviceDto requestDevice);
}
