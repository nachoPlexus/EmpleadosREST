package com.plexus.directory.domain.model;

import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@Table(name = "devices")
public class DeviceDto {
    private int id;

    @NonNull
    private String serialNumber;

    private String brand;

    private String model;

    @NonNull
    private String os;

    private int assignedTo;
}