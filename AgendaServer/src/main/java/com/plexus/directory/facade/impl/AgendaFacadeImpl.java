package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.facade.AgendaFacade;
import com.plexus.directory.service.impl.AgendaServiceAsync;
import com.plexus.directory.service.impl.AgendaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.View;

import java.util.List;

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
    public List<EmployeeDto> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeName(String deviceName, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeSurname(String deviceSurname, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public List<EmployeeDto> getByEmployeeId(String dev, int resolvedPage, int resolvedSize) {
        return List.of();
    }

    @Override
    public int save(List<EmployeeDto> devices) {
        return 0;
    }

    @Override
    public int update(List<EmployeeDto> devices) {
        return 0;
    }
}
