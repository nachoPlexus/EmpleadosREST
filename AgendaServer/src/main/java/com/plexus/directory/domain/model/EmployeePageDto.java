package com.plexus.directory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePageDto {
    private List<EmployeeDto> employees;
    private int pageNumber;
    private int totalEmployees;
}
