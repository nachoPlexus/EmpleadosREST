package com.plexus.directory.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeePageResponse {
    private List<EmployeeDto> employees;
    private int pageNumber;
    private int totalEmployees;
}
