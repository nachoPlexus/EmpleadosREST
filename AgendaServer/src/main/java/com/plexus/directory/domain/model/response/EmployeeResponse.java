package com.plexus.directory.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private int employeeId;
    private String employeeName;
    private String employeeSurname;
    private String plexusMail;
    private String clientMail;
    private int phoneNumber;
    private DeviceResponse assignedDevice;
}
