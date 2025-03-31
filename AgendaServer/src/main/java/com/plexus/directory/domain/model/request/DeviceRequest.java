package com.plexus.directory.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRequest {
    private String serialNumber;
    private String brand;
    private String model;
    private String operatingSystem;
}
