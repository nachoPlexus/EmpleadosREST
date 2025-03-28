package com.plexus.directory.domain.model.response;

import com.plexus.directory.domain.model.DeviceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DevicePageResponse {
    private List<DeviceDto> deviceDtos;
    private int totalDevices;
    private int pageNumber;
}
