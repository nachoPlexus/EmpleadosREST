package com.plexus.directory.web;

import com.plexus.directory.domain.model.request.EmployeeRequest;
import com.plexus.directory.domain.model.response.EmployeePageResponse;
import com.plexus.directory.facade.AgendaFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8082")
@RequestMapping("/agenda/employees")
public class AgendaController {
    private final AgendaFacade facade;

    public AgendaController(AgendaFacade facade) {
        this.facade = facade;
    }

    @GetMapping({"/{page}/{size}", "/", ""})
    public ResponseEntity<EmployeePageResponse> getEmployees(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) Integer size) {
        //los page y size por defecto, les resta uno en servidor para "humanizar", por eso 2/11 y no 1/10
        int resolvedPage = (page != null) ? page : 2;
        int resolvedSize = (size != null) ? size : 11;

        return facade.getEmployeesPaged(resolvedPage, resolvedSize);
    }

    @GetMapping("/filter/{filterValue}")
    public ResponseEntity<EmployeePageResponse> getFilteredEmployees(
            @PathVariable String filterValue,
            @RequestParam(defaultValue = "2") int page,
            @RequestParam(defaultValue = "11") int size,
            @RequestParam String filterType) {
        return switch (filterType) {
            case "name" -> facade.getEmployeesByName(filterValue, page, size);
            case "surname" -> facade.getEmployeesBySurname(filterValue, page, size);
            case "id" -> ResponseEntity.ok(
                    new EmployeePageResponse(
                            List.of(facade.getEmployeeById(Integer.valueOf(filterValue)).getBody())
                            , 1
                            , 1)
            );
            default -> throw new IllegalStateException("Unexpected value: " + filterType);
        };
    }

    @PostMapping
    public ResponseEntity<String> createDevices( @RequestBody List<EmployeeRequest> employeeRequests) {
        return facade.createEmployees(employeeRequests);
    }

    @PutMapping
    public ResponseEntity<String> updateDevices( @RequestBody List<EmployeeRequest> employeeRequests) {
        return facade.updateEmployee(employeeRequests);
    }

}
