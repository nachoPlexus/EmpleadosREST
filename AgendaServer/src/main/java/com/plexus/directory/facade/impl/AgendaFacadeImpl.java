package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.DevicePageResponse;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.DeviceDto;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.mapper.DeviceMapper;
import com.plexus.directory.facade.AgendaFacade;
import com.plexus.directory.service.impl.AgendaServiceAsync;
import com.plexus.directory.service.impl.AgendaServiceImpl;
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
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.HashMap;
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
public class AgendaFacadeImpl implements AgendaFacade {
    private final AgendaServiceImpl service;
    private final AgendaServiceAsync asyncService;
    private final Validator validator;
    private final View error;

    @Autowired
    public AgendaFacadeImpl(AgendaServiceImpl service, AgendaServiceAsync asyncService, Validator validator, View error) {
        this.service = service;
        this.asyncService = asyncService;
        this.validator = validator;
        this.error = error;
    }


    @Override
    public List<Employee> getAll(int page, int size) {
        return service.getAll(page,size);
    }

    @Override
    public List<Employee> getByEmployeeName(String deviceName, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public List<Employee> getByEmployeeSurname(String deviceSurname, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public List<Employee> getByEmployeeId(String dev, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public int save(List<Employee> devices) {
        return 0;
    }

    @Override
    public int update(List<Employee> devices) {
        return 0;
    }
}
