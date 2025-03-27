package com.plexus.directory.facade;

import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DeviceFacade {

    ResponseEntity<DevicePageResponse> getDevicesPaged(int page, int size);

    ResponseEntity<DeviceDto> getDeviceById(int deviceId) ;

    ResponseEntity<DevicePageResponse> getDevicesByBrand(String brand, int resolvedPage, int resolvedSize);

    ResponseEntity<String> createDevices(List<DeviceDto> devicesDto);

    ResponseEntity<Map<String, Object>> updateDevice(@Valid List<DeviceDto> devices);

    ResponseEntity<String> deleteDevices(List<DeviceDto> deviceDto);

}
