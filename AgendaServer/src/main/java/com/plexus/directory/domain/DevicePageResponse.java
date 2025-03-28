package com.plexus.directory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DevicePageResponse {
    private List<Device> devices;
    private int totalDevices;
    private int pageNumber;
}
