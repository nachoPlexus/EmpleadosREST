package com.plexus.directory.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @NonNull
    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @NonNull
    @Column(name = "os", nullable = false)
    private String os;

    @Column(name = "assigned_to", nullable = false)
    private int assignedTo;
}