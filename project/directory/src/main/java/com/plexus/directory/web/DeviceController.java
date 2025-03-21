package com.plexus.directory.web;

import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.facade.DeviceFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceFacade facade;

    public DeviceController(DeviceFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<DevicePageResponse> getDevices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return facade.getDevicesPaged(page-1, size);
    }

    @GetMapping("/id/{deviceId}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable int deviceId) {
        return facade.getDeviceById(deviceId);
    }

    @GetMapping("/brand/{deviceBrand}")
    public ResponseEntity<DevicePageResponse> getDevicesByBrand(@PathVariable String deviceBrand) {
        return facade.getDevicesByBrand(deviceBrand);
    }

    @PostMapping
    public ResponseEntity<String> createDevice(@Valid @RequestBody DeviceDto device) {
        return facade.createDevice(device);
    }

    @PutMapping
    public ResponseEntity<String> updateDevice(@Valid @RequestBody DeviceDto device) {
        return facade.updateDevice( device);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable int deviceId) {
        return facade.deleteDevice(deviceId);
    }
}
