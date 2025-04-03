package com.plexus.directory.web;

import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.facade.DeviceFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceFacade facade;
    public DeviceController(DeviceFacade facade) {
        this.facade = facade;
    }

    @GetMapping({"/{page}/{size}", ""})
    public ResponseEntity<DevicePageResponse> getDevices(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) Integer size) {

        int resolvedPage = (page != null) ? page - 1 : 0;
        int resolvedSize = (size != null) ? size : 10;

        return facade.getDevicesPaged(resolvedPage, resolvedSize);
    }

    @GetMapping("/id/{deviceId}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable int deviceId) {
        return facade.getDeviceById(deviceId);
    }

    @GetMapping("/brand/{deviceBrand}")
    public ResponseEntity<DevicePageResponse> getDevicesByBrand(
            @PathVariable String deviceBrand,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return facade.getDevicesByBrand(deviceBrand,page-1,size);
    }

    @GetMapping("/assignation/{deviceId}")
    public ResponseEntity<DeviceDto>getByAssignatedPatient(@PathVariable int deviceId){
        return facade.getByAssignatedEmployee(deviceId);
    }

    @PostMapping
    public ResponseEntity<String> createDevices( @RequestBody List<DeviceDto> devices) {
        return facade.createDevices(devices);
    }

    @PutMapping
    public ResponseEntity<String> updateDevices(@Valid @RequestBody List<DeviceDto> devices) {
        return facade.updateDevice(devices);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDevices(@RequestBody List<DeviceDto> deviceDtos) {
        return facade.deleteDevices(deviceDtos);
    }

}