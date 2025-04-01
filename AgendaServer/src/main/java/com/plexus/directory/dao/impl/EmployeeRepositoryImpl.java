package com.plexus.directory.dao.impl;

import com.plexus.directory.dao.EmployeeRepository;
import com.plexus.directory.dao.GenericRepository;
import com.plexus.directory.dao.retrofit.llamadas.EmployeesApi;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

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
        return safeApiCall(api.getEmployees(page,size));
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
    public int save(List<EmployeeDto> employees) {
        int sizesaved = 0;
        for (EmployeeDto e:employees){
            String result = safeApiCall(api.createEmployee(e));
            if (result.equalsIgnoreCase("Employee creado bien"))
                sizesaved++;
        }
        if (sizesaved!=employees.size())
            throw new StatusException(Map.of("SavingError","Se han guardado menos employees que los enviados"));
        return 1;
    }

    @Override
    public int update(List<EmployeeDto> employees) {
        int sizeUpdated = 0;
        for (EmployeeDto e:employees){
            String result = safeApiCall(api.updateEmployee(e));
            if (result.equalsIgnoreCase("Employee actualizado bien"))
                sizeUpdated++;
        }
        if (sizeUpdated!= employees.size())
            throw new StatusException(Map.of("UpdatingError","Se han actualizado menos employees que los enviados"));
        return 1;
    }

    @Override
    public int delete(List<EmployeeDto> employees){
        int sizeUpdated = 0;
        for (EmployeeDto e:employees){
            String result = safeApiCall(api.deleteEmployee(e.getId()));
            if (result.equalsIgnoreCase("Employee actualizado bien"))
                sizeUpdated++;
        }
        if (sizeUpdated!= employees.size())
            throw new StatusException(Map.of("UpdatingError","Se han actualizado menos employees que los enviados"));
        return 1;

    }
}
