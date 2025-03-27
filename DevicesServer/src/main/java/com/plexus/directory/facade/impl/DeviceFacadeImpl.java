package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.dto.DevicePageResponse;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.facade.DeviceFacade;
import com.plexus.directory.service.impl.DeviceServiceAsync;
import com.plexus.directory.service.impl.DeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.plexus.directory.common.Constants.ERROR_A_MEDIAS_DELETE_DEVICE;
import static com.plexus.directory.common.Constants.INVISIBLE;

@Component
@Profile("versionBase")
@EnableAsync
@Slf4j
public class DeviceFacadeImpl implements DeviceFacade {
    private final DeviceServiceImpl service;
    private final DeviceMapper mapper;
    private final DeviceServiceAsync asyncService;
    private final Validator validator;

    @Autowired
    public DeviceFacadeImpl(DeviceServiceImpl service, DeviceMapper mapper, DeviceServiceAsync asyncService, Validator validator) {
        this.service = service;
        this.mapper = mapper;
        this.asyncService = asyncService;
        this.validator = validator;
    }

    private static List<Device> wipeAllExceptIds(List<DeviceDto> devicesDto) {
        return devicesDto.stream()
                .map(dto -> {
                    Device device = new Device();
                    device.setId(dto.getId());
                    return device;
                })
                .toList();
    }

    @Override
    public ResponseEntity<DevicePageResponse> getDevicesPaged(int page, int size) {
        CompletableFuture<Integer> totalSizeFuture = asyncService.getDevicesCountAsync();
        List<DeviceDto> deviceDtos = service.getAll(page, size).stream().map(mapper::toDto).toList();

        int totalEntities;
        try {
            totalEntities = totalSizeFuture.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("error: Hilo interrumpido al contar devices:", e);
            totalEntities = deviceDtos.size();
        } catch (ExecutionException e) {
            log.error("error al contar los devices: ", e);
            totalEntities = deviceDtos.size();
        }
        DevicePageResponse response = new DevicePageResponse(deviceDtos, totalEntities, page + 1);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DeviceDto> getDeviceById(int deviceId) {
        return ResponseEntity.ok(mapper.toDto(service.getById(deviceId)));
    }

    @Override
    public ResponseEntity<DevicePageResponse> getDevicesByBrand(String brand, int resolvedPage, int resolvedSize) {

        List<DeviceDto> devices = service.getByBrand(brand, resolvedPage, resolvedSize)
                .stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(new DevicePageResponse(devices, devices.size(), 1));
    }

    @Override
    public ResponseEntity<String> createDevices(List<DeviceDto> devicesDto) {
        Result validationResult = validateDevicesList(devicesDto);
        int result = service.save(validationResult.validDevices);

        return result > validationResult.validDevices().size()
                ? ResponseEntity.status(HttpStatus.CREATED).body("Devices creados bien")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INVISIBLE);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateDevice(List<DeviceDto> devicesDto) {
        Result validationResult = validateDevicesList(devicesDto);
        int result = service.update(validationResult.validDevices);

        return result == validationResult.validDevices.size()
                ? ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Hurra!", "Todos los devices actualizados bien"))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("inesperado", INVISIBLE));

    }

    @Override
    public ResponseEntity<String> deleteDevices(List<DeviceDto> devicesDto) {
        List<Device> devices = wipeAllExceptIds(devicesDto);

        int result = service.delete(devices);
        return result == devices.size()
                ? ResponseEntity.status(HttpStatus.OK).body("Devices borrados bien")
                : ResponseEntity.status(HttpStatus.MULTI_STATUS).body(ERROR_A_MEDIAS_DELETE_DEVICE);
    }

    private Result validateDevicesList(List<DeviceDto> devicesDto) throws StatusException {
        List<Device> validDevices = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (DeviceDto dto : devicesDto) {
            Errors tempErrors = new BeanPropertyBindingResult(dto, "deviceDto");
            validator.validate(dto, tempErrors);
            if (tempErrors.hasErrors()) {
                errors.add("Error en el dispositivo con serial " + dto.getSerialNumber() + ": " + tempErrors.getAllErrors());
            } else {
                validDevices.add(mapper.toEntity(dto));
            }
        }

        if (!errors.isEmpty()) {
            throw new StatusException(
                    Map.of("Message", "Se han invalidado " + errors.size() + " de " + devicesDto.size() + " devices. Se muestran los errores de los devices que no han sido actualizados porque son invalidos: ",
                            "Errores", errors));
        }

        return new Result(validDevices, errors);
    }

    private record Result(List<Device> validDevices, List<String> errors) {
    }
}
