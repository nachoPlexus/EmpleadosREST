package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.dao.retrofit.llamadas.EmployeesApi;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Profile("versionBase")
public class EmployeeRepositoryImpl extends GenericRepository implements EmployeeRepository {
    private final EmployeesApi api;

    public EmployeeRepositoryImpl(EmployeesApi api) {
        this.api = api;
    }


    @Override
    public EmployeePageDto getAll(int page, int size) {
        return safeApiCall(api.getEmployees(page, size));
    }

    @Override
    public EmployeePageDto getByEmployeeName(String employeeName, int page, int size) {
        return safeApiCall(api.getEmployeesByName(employeeName, page, size));
    }

    @Override
    public EmployeePageDto getByEmployeeSurname(String employeeSurname, int page, int size) {
        return safeApiCall(api.getEmployeesBySurname(employeeSurname, page, size));
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        return safeApiCall(api.getEmployeeById(id));
    }

    @Override
    public List<Integer> save(List<EmployeeDto> employees) {
        List<Integer> ids = new ArrayList<>();
        List<Map<String, Object>> errors = new ArrayList<>();
        int totalEmployees = employees.size();

        for (EmployeeDto e : employees) {
            try {
                String result = safeApiCall(api.createEmployee(e));
                ids.add(extractIdFromResponse(result));
            } catch (StatusException | NumberFormatException ex) {
                errors.add(Map.of("employee", e,
                        "error", ex.getMessage(),
                        "type", ex.getClass().getSimpleName()
                ));
            }
        }
        if (!errors.isEmpty()) {
            throw new StatusException(Map.of("SavingError", "Error al guardar empleados, haciendo rollback",
                    "successful", ids.size(),
                    "failed", totalEmployees - ids.size(),
                    "errors", errors
            ));
        }
        return ids;
    }

    private int extractIdFromResponse(String response) throws NumberFormatException, StatusException{
        String prefix = "Employee creado bien con id: ";
        if (!response.contains(prefix)) {
            throw new StatusException(Map.of(
                    "InvalidResponse", "Formato de respuesta no esperado",
                    "response", response
            ));
        }
        return Integer.parseInt(response.substring(response.length() - 1));
    }

    @Override
    public int update(List<EmployeeDto> employees) {
        int sizeUpdated = 0;
        for (EmployeeDto e : employees) {
            String result = safeApiCall(api.updateEmployee(e));
            if (result.equalsIgnoreCase("Employee actualizado bien"))
                sizeUpdated++;
        }
        if (sizeUpdated != employees.size())
            throw new StatusException(Map.of("UpdatingError", "Se han actualizado menos employees que los enviados"));
        return 1;
    }

    @Override
    public int delete(List<EmployeeDto> employees) {
        int sizeUpdated = 0;
        for (EmployeeDto e : employees) {
            String result = safeApiCall(api.deleteEmployee(e.getId()));
            if (result.equalsIgnoreCase("Employee actualizado bien"))
                sizeUpdated++;
        }
        if (sizeUpdated != employees.size())
            throw new StatusException(Map.of("UpdatingError", "Se han actualizado menos employees que los enviados"));
        return 1;

    }
}
