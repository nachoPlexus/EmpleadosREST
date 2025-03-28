package com.plexus.directory.facade;

import com.plexus.directory.domain.DevicePageResponse;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.dto.DeviceDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AgendaFacade {

    List<Employee> getAll(int page, int size);

    List<Employee> getByEmployeeName(String deviceName, int resolvedPage, int resolvedSize);
    List<Employee> getByEmployeeSurname(String deviceSurname, int resolvedPage, int resolvedSize);
    List<Employee> getByEmployeeId(String dev, int resolvedPage, int resolvedSize);

    int save(List<Employee> devices);

    int update(List<Employee> devices);

}
