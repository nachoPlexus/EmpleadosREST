package com.plexus.directory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeePageResponse {
    private List<EmployeeDto> employees;
    private int totalEmployees;
    private int pageNumber;
}
