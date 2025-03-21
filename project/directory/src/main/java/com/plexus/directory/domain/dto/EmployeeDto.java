package com.plexus.directory.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id; // Puede ser nulo al crear

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener máximo 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 150, message = "El apellido debe tener máximo 150 caracteres")
    private String surname;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Size(max = 200, message = "El mailPlexus debe tener máximo 200 caracteres")
    private String mailPlexus;

    @Email(message = "El correo de cliente debe ser válido")
    @Size(max = 200, message = "El mailClient debe tener máximo 200 caracteres")
    private String mailClient;

    @Size(max = 50, message = "El clientId debe tener máximo 50 caracteres")
    private String clientId;

    @Pattern(regexp = "\\d{9}", message = "El phoneNumber debe ser numérico de 9 dígitos")
    private String phoneNumber;

    private String phoneSerialNumber;
}
