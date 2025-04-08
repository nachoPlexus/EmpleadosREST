package com.plexus.directory.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private int id;
    @NotNull(message = "El serialNumber no puede ser nulo")
    private String serialNumber;
    private String brand;
    private String model;
    @NotNull(message = "El sistema operativo (os) no puede ser nulo")
    private String os;
    private int assignedTo;
}