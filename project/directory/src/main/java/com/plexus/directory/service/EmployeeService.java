package com.plexus.directory.service;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.error.NoContentException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository dao;

    public EmployeeService(EmployeeRepository dao) {
        this.dao = dao;
    }

    public List<Employee> getAll() {
        try {
            List<Employee> employees = dao.getAll();
            if (employees.isEmpty()) {
                return Collections.emptyList();
            }
            return employees;
        } catch (Exception e) {
            throw new DataBaseException("error tecnico, pruebe de nuevo "+e.getMessage());
        }
    }

    public Employee getById(int employeeId) {
        try {
            Employee employee = dao.get(employeeId);
            if (employee == null) {
                throw new NoContentException("No se encontró empleado con ID: " + employeeId);
            }
            return employee;
        }catch (Exception e) {
            throw new DataBaseException("error tecnico, pruebe de nuevo "+e.getMessage());
        }
    }

    public Employee getByName(String employeeName) {
        if (employeeName.length() > 50) {
            throw new BadRequestException("El nombre debe tener una longitud máxima de 50 caracteres");
        }

        try {
            Employee employee = dao.get(employeeName);
            if (employee == null) {
                throw new NoContentException("No se encontró empleado con nombre: " + employeeName);
            }
            return employee;
        }catch (Exception e) {
            throw new DataBaseException("error tecnico, pruebe de nuevo "+e.getMessage());
        }
    }

    public int save(Employee employee) {
        validateEmployeeForSave(employee);
        return dao.save(employee);
    }

    private void validateEmployeeForSave(Employee employee) {
        if (employee.getName() == null || employee.getName().isBlank()) {
            throw new BadRequestException("El campo 'name' es obligatorio");
        }
        if (employee.getName().length() > 50) {
            throw new BadRequestException("El nombre debe tener una longitud máxima de 50 caracteres");
        }

        if (employee.getSurname() == null || employee.getSurname().isBlank()) {
            throw new BadRequestException("El campo 'surname' es obligatorio");
        }
        if (employee.getSurname().length() > 150) {
            throw new BadRequestException("El apellido debe tener una longitud máxima de 150 caracteres");
        }

        if (employee.getMailPlexus() == null || employee.getMailPlexus().isBlank()) {
            throw new BadRequestException("El campo 'mailPlexus' es obligatorio");
        }
        if (!employee.getMailPlexus().contains("@") || !employee.getMailPlexus().contains(".")) {
            throw new BadRequestException("El mailPlexus debe contener '@' y '.'");
        }
        if (employee.getMailPlexus().length() > 200) {
            throw new BadRequestException("El mailPlexus debe tener una longitud máxima de 200 caracteres");
        }

        if (employee.getClientId() != null && employee.getClientId().length() > 50) {
            throw new BadRequestException("El clientId debe tener una longitud máxima de 50 caracteres");
        }

        if (employee.getMailClient() != null) {
            if (!employee.getMailClient().contains("@") || !employee.getMailClient().contains(".")) {
                throw new BadRequestException("El mailClient debe contener '@' y '.'");
            }
            if (employee.getMailClient().length() > 200) {
                throw new BadRequestException("El mailClient debe tener una longitud máxima de 200 caracteres");
            }
        }

        if (employee.getPhoneNumber() != null) {
            if (employee.getPhoneNumber().length() != 9) {
                throw new BadRequestException("El phoneNumber debe ser numérico de 9 dígitos");
            }
        }
    }

    @Transactional
    public int update(Employee employee) {
        validateEmployeeForUpdate(employee);
        return dao.update(employee);
    }

    private void validateEmployeeForUpdate(Employee employee) {
        if (employee.getClientId() != null && employee.getClientId().length() > 50) {
            throw new BadRequestException("El clientId debe tener una longitud máxima de 50 caracteres");
        }

        if (employee.getMailClient() != null) {
            if (!employee.getMailClient().contains("@") || !employee.getMailClient().contains(".")) {
                throw new BadRequestException("El mailClient debe contener '@' y '.'");
            }
            if (employee.getMailClient().length() > 200) {
                throw new BadRequestException("El mailClient debe tener una longitud máxima de 200 caracteres");
            }
        }

        if (employee.getPhoneNumber() != null) {
            if (employee.getPhoneNumber().length() != 9) {
                throw new BadRequestException("El phoneNumber debe ser numérico de 9 dígitos");
            }
        }
    }

    public boolean delete(Employee employee) {
        return dao.delete(employee);
    }
}
