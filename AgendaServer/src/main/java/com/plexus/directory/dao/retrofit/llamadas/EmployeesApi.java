package com.plexus.directory.dao.retrofit.llamadas;

import com.plexus.directory.domain.model.EmployeeDto;
import com.plexus.directory.domain.model.EmployeePageDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface EmployeesApi {

    @GET("employees")
    Call<EmployeePageDto> getEmployees(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("employees/id/{employeeId}")
    Call<EmployeeDto> getEmployeeById(
            @Path("employeeId") int employeeId
    );

    @GET("employees/name/{employeeName}")
    Call<EmployeePageDto> getEmployeesByName(
            @Path("employeeName") String employeeName,
            @Query("page") int page,
            @Query("size") int size
    );

    @POST("employees")
    Call<String> createEmployee(
            @Body EmployeeDto employee
    );

    @PUT("employees")
    Call<String> updateEmployee(
            @Body EmployeeDto employee
    );

    @PUT("employees/surnames")
    Call<String> updateAllSurnames();

    @DELETE("employees/{employeeId}")
    Call<String> deleteEmployee(
            @Path("employeeId") int employeeId
    );
}