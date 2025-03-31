package com.plexus.directory.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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