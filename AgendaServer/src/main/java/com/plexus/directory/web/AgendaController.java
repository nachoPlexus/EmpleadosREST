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

    //TODO LLLAMADA A GETALL(CON PAGINACION)
    @GetMapping({"/{page}/{size}", "/"})
    public ResponseEntity<EmployeePageResponse> getEmployees(
            @PathVariable(required = false) Integer page,
            @PathVariable(required = false) Integer size) {
        //los page y size por defecto
        int resolvedPage = (page != null) ? page - 1 : 1;
        int resolvedSize = (size != null) ? size : 10;

        return facade.getEmployeesPaged(resolvedPage, resolvedSize);
    }

    @GetMapping("/filter/{filterValue}")
    public ResponseEntity<EmployeePageResponse> getFilteredEmployees(
            @PathVariable String filterValue,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "10") String filterType) {
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

    //TODO LLAMADA AL ADD EMPLOYEE CON O SIN DEVICES
    @PostMapping
    public ResponseEntity<String> createDevices( @RequestBody List<EmployeeRequest> employeeRequests) {
        return facade.createEmployees(employeeRequests);
    }

    //TODO LLAMADA AL UPDATE EMPLOYEE CON DEVICES
    @PutMapping
    public ResponseEntity<String> updateDevices( @RequestBody List<EmployeeRequest> employeeRequests) {
        return facade.updateEmployee(employeeRequests);
    }

}
