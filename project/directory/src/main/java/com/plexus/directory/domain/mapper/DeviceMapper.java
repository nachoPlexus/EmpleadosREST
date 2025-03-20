package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.dto.DeviceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.text.html.parser.Entity;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceDto toDto(Device response);

    Device toEntity(DeviceDto request);

}
