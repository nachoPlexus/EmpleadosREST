package com.plexus.directory.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevicePageResponse {
    private List<DeviceResponse> deviceDtos;
    private int pageNumber;
    private int totalDevices;
}
