package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.dto.DeviceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceDto toDto(Device responseDevice);

    Device toEntity(DeviceDto requestDevice);

}
