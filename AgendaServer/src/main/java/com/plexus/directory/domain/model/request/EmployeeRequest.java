package com.plexus.directory.domain.model.request;

import jakarta.validation.constraints.Email;
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
public class EmployeeRequest {
    private int id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener máximo 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 150, message = "El apellido debe tener máximo 150 caracteres")
    private String surname;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 200, message = "El mailPlexus debe tener máximo 200 caracteres")
    @Email(message = "El correo debe ser válido")
    private String plexusMail;

    @Email(message = "El correo de cliente debe ser válido")
    @Size(max = 200, message = "El mailClient debe tener máximo 200 caracteres")
    private String clientMail;

    @Pattern(regexp = "\\d{9}", message = "El phoneNumber debe ser numérico de 9 dígitos")
    private String phoneNumber;

    private DeviceRequest assignedDevice;
    private boolean deleteEmployee;
    private boolean deleteAssignedDevice;
}