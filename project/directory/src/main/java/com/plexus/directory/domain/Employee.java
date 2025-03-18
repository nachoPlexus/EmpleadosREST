package com.plexus.directory.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SQLite usa AUTOINCREMENT
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


    public Employee(ResultSet rs) throws SQLException {
        this.id= rs.getInt("id");
        this.name= rs.getString("name");
        this.surname= rs.getString("surname");
        this.mailPlexus=rs.getString("mail_plexus");
        this.mailClient=rs.getString("mail_client");
        this.clientId=rs.getString("client_id");
        this.phoneNumber=rs.getString("phone_number");
    }
}
