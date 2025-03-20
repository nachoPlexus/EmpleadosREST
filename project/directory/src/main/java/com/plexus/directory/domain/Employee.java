package com.plexus.directory.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NonNull
    @Column(name = "mail_plexus", nullable = false, unique = true)
    private String mailPlexus;

    @Column(name = "mail_client")
    private String mailClient;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_serial_number")
    private String phoneSerialNumber;
}
