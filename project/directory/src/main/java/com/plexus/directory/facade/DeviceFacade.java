package com.plexus.directory.facade;

import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import org.springframework.http.ResponseEntity;

public interface DeviceFacade {

    ResponseEntity<DevicePageResponse> getDevicesPaged(int page, int size);

    ResponseEntity<DeviceDto> getDeviceById(int deviceId) ;

    ResponseEntity<DevicePageResponse> getDevicesByBrand(String brand);

    ResponseEntity<String> createDevice(DeviceDto deviceDTO);
    ResponseEntity<String> updateDevice(DeviceDto deviceDTO) ;

    ResponseEntity<String> deleteDevice(int deviceId);
}
