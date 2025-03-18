package com.plexus.directory.web;

import com.plexus.directory.domain.Employee;
import com.plexus.directory.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    //GET /employees
    @GetMapping
    public List<Employee> getEmployees(){
        return service.getAll();
    }

    //GET /employees/id/{id}
    @GetMapping
    @RequestMapping("/id/{employeeId}")
    private Employee getEmployeeById(@PathVariable int employeeId){
        return service.getById(employeeId);
    }

    //GET /employees/name/{name}
    @GetMapping
    @RequestMapping("/name/{employeeName}")
    private Employee getEmployeeByName(@PathVariable String employeeName){
        return service.getByName(employeeName);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        return service.save(employee) > 0 ? ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully")
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is no way anyone can read this text");
    }

    @PutMapping
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        int updated = service.update(employee);
        return updated > 0 ? ResponseEntity.ok("Employee updated successfully")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is no way anyone can read this text");
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId) {
        Employee employee = service.getById(employeeId);
        if (employee != null && service.delete(employee)) {
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is no way anyone can read this text");
    }
}
