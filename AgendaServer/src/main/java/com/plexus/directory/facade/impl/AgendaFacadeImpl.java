package com.plexus.directory.facade.impl;

import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.model.request.DeviceRequest;
import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.domain.model.response.EmployeeResponse;
import com.plexus.directory.facade.AgendaFacade;
import com.plexus.directory.service.impl.AgendaServiceAsync;
import com.plexus.directory.service.impl.AgendaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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


@Component
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
    public ResponseEntity<EmployeePageResponse> getEmployeesPaged(int page, int size) {
        EmployeePageResponse employees = service.getEmployeesPaged(page,size);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesByName(String employeeName, int resolvedPage, int resolvedSize) {
        EmployeePageResponse employees = service.getEmployeesByName(employeeName, resolvedPage, resolvedSize);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<EmployeePageResponse> getEmployeesBySurname(String employeeSurname, int resolvedPage, int resolvedSize) {
        EmployeePageResponse employees = service.getEmployeesBySurname(employeeSurname, resolvedPage, resolvedSize);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(int employeeId) {
        EmployeeResponse employees = service.getEmployeeById(employeeId);
        return ResponseEntity.ok().body(employees);
    }

    @Override
    public ResponseEntity<String> createEmployees(List<EmployeeRequest> employeeRequests) {
        ValidationEmployeesResult validationEmployeesResult = validateEmployeesList(employeeRequests);
        String result = service.createEmployee(validationEmployeesResult.validEmployees());

        return result.equals("ok")
                ? ResponseEntity.status(HttpStatus.CREATED).body("Todos los empleados creados correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    }

    @Override
    public ResponseEntity<String> updateEmployee(List<EmployeeRequest> employeeRequests) {
        ValidationEmployeesResult validationEmployeesResult = validateEmployeesList(employeeRequests);
        String result = service.updateEmployee(validationEmployeesResult.validEmployees());

        return result.equals("ok")
                ? ResponseEntity.status(HttpStatus.CREATED).body("Todos los empleados actualizados correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
    }

    private ValidationEmployeesResult validateEmployeesList(List<EmployeeRequest> employeeRequests) {
        List<EmployeeRequest> validEmployees = new ArrayList<>();
        Map<String, Object> errors = new HashMap<>();

        for (EmployeeRequest request : employeeRequests) {
            Errors validationErrors = new BeanPropertyBindingResult(request, "employeeRequest");
            validator.validate(request, validationErrors);

            if (request.getAssignedDevice() != null) {
                Errors deviceErrors = new BeanPropertyBindingResult(request.getAssignedDevice(), "deviceRequest");
                validator.validate(request.getAssignedDevice(), deviceErrors);

                if (deviceErrors.hasErrors()) {
                    errors.put("Error en el dispositivo asignado al empleado llamado " + request.getName()+" "+request.getSurname(),
                            deviceErrors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
                }
            }

            if (validationErrors.hasErrors()) {
                errors.put("Error en el empleado con nombre " + request.getName(),
                        validationErrors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
            } else {
                validEmployees.add(request);
            }
        }

        if (!errors.isEmpty()) {
            throw new StatusException(Map.of(
                    "Message", "Se han invalidado " + errors.size() + " de " + employeeRequests.size() + " empleados y/o dispositivos.",
                    "Errores", errors));
        }

        return new ValidationEmployeesResult(validEmployees, errors);
    }
    private record ValidationEmployeesResult(List<EmployeeRequest> validEmployees, Map<String, Object> errors) { }
}
