package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.facade.DeviceFacade;
import com.plexus.directory.service.impl.DeviceServiceImpl;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Profile("versionBase")
public class DeviceFacadeImpl implements DeviceFacade {
    private final DeviceServiceImpl service;
    private final DeviceMapper mapper;

    public DeviceFacadeImpl(DeviceServiceImpl service, DeviceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<DevicePageResponse> getDevicesPaged(int page, int size) {
        List<DeviceDto> deviceDtos = service.getAll().stream().map(mapper::toDto).toList();

        DevicePageResponse response = new DevicePageResponse(deviceDtos, deviceDtos.size(), page+1);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DeviceDto> getDeviceById(int deviceId) {
        return ResponseEntity.ok(mapper.toDto(service.getById(deviceId)));
    }

    @Override
    public ResponseEntity<DevicePageResponse> getDevicesByBrand(String brand) {

        List<DeviceDto> devices = service.getByBrand(brand)
                .stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(new DevicePageResponse(devices, devices.size(), 1));
    }

    @Override
    public ResponseEntity<String> createDevice(DeviceDto deviceDTO) {

        int result = service.save(mapper.toEntity(deviceDTO));
        return result > 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Device creado bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se va a ver esto nunca, antes salta exception");

    }

    @Override
    public ResponseEntity<String> updateDevice(@Valid DeviceDto deviceDTO) {
        int updated = service.update(mapper.toEntity(deviceDTO));
        return updated > 0 ? ResponseEntity.ok("Device actualizado bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se va a ver esto nunca, antes salta exception");
    }

    @Override
    public ResponseEntity<String> deleteDevice(int deviceId) {
        Device device = service.getById(deviceId);
        return service.delete(device) ? ResponseEntity.ok("Device eliminado correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo eliminar el device");
    }
}
