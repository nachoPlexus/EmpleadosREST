package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.dao.retrofit.llamadas.EmployeesApi;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@Profile("versionBase")
public class EmployeeRepositoryImpl extends GenericRepository implements EmployeeRepository {
    private final EmployeesApi api;

    public EmployeeRepositoryImpl(EmployeesApi api) {
        this.api = api;
    }


    @Override
    public EmployeePageDto getAll(int page, int size) {
        return safeApiCall(api.getEmployees(page, size),EmployeePageDto.class);
    }

    @Override
    public EmployeePageDto getByEmployeeName(String employeeName, int page, int size) {
        return safeApiCall(api.getEmployeesByName(employeeName, page, size),EmployeePageDto.class);
    }

    @Override
    public EmployeePageDto getByEmployeeSurname(String employeeSurname, int page, int size) {
        return safeApiCall(api.getEmployeesBySurname(employeeSurname, page, size),EmployeePageDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        return safeApiCall(api.getEmployeeById(id),EmployeeDto.class);
    }

    @Override
    public List<Integer> save(List<EmployeeDto> employees) {
        List<Integer> ids = new ArrayList<>();
        List<Map<String, Object>> errors = new ArrayList<>();
        int totalEmployees = employees.size();

        for (EmployeeDto e : employees) {
            try {
                String result = safeApiCall(api.createEmployee(e), ResponseBody.class).string();
                ids.add(extractIdFromResponse(result));
            } catch (StatusException | NumberFormatException | IOException ex) {
                errors.add(Map.of("employee", e!=null?e:"empleado nulo",
                        "error", ex
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

    private int extractIdFromResponse(String response) throws NumberFormatException, StatusException {
        String prefix = "Employee creado bien con id: ";
        if (!response.startsWith(prefix)) {
            throw new StatusException(Map.of(
                    "InvalidResponse", "Formato de respuesta no esperado",
                    "response", response
            ));
        }

        try {
            return Integer.parseInt(response.substring(prefix.length()).trim());
        } catch (NumberFormatException e) {
            throw new StatusException(Map.of(
                    "InvalidResponse", "No se pudo extraer el ID del empleado",
                    "response", response
            ));
        }
    }


    @Override
    public int update(List<EmployeeDto> employees) {
        int sizeUpdated = 0;
        for (EmployeeDto e : employees) {
            String result = safeApiCall(api.updateEmployee(e),String.class);
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
            String result = safeApiCall(api.deleteEmployee(e.getId()),String.class);
            if (result.equalsIgnoreCase("Employee actualizado bien"))
                sizeUpdated++;
        }
        if (sizeUpdated != employees.size())
            throw new StatusException(Map.of("UpdatingError", "Se han actualizado menos employees que los enviados"));
        return 1;

    }
}
