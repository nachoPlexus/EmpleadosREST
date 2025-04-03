package com.plexus.directory.domain.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRequest {
    @NotBlank(message = "El serialNumber es obligatorio")
    @Pattern(regexp = "[a-zA-Z0-9]{11}", message = "tiene que ser alfanumerico y tener exactamente 11 caracteres")
    private String serialNumber;

    @NotBlank(message = "brand es obligatorio")
    @Size(max = 50, message = "brand debe tener máximo 50 caracteres")
    private String brand;

    private String model;

    @NotBlank(message = "sistema operativo es obligatorio")
    @Size(max = 30, message = "sistema operativo debe tener máximo 50 caracteres")
    private String operatingSystem;
}
