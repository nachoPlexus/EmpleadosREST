package com.plexus.directory.domain.model.request;

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
    private String name;
    private String surname;
    private String plexusMail;
    private String clientMail;
    private String phoneNumber;
    private DeviceRequest assignedDevice;
    private boolean deleteEmployee;
    private boolean deleteAssignedDevice;
}