package com.plexus.directory.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceResponse {
    private int deviceId;
    private String serialNumber;
    private String brand;
    private String model;
    private String operatingSystem;
}
