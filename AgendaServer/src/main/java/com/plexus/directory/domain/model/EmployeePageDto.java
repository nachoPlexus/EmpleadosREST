package com.plexus.directory.domain.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePageDto {
    private List<EmployeeDto> employees;
    private int totalEmployees;
    private int pageNumber;
}
