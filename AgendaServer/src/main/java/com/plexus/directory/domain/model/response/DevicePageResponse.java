package com.plexus.directory.domain.model.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevicePageResponse {
    private List<DeviceResponse> deviceDtos;
    private int totalDevices;
    private int pageNumber;
}
