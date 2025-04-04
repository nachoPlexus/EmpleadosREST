package com.plexus.directory.domain.model;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String mailPlexus;

    private String mailClient;

    private String clientId;

    private Integer  phoneNumber;

    private String phoneSerialNumber;
}
