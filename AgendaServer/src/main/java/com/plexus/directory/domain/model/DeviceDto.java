package com.plexus.directory.domain.model;

import jakarta.persistence.*;
import lombok.*;

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