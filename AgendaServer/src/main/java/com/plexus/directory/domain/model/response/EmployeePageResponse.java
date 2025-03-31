package com.plexus.directory.domain.model.response;

import com.plexus.directory.domain.model.EmployeeDto;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePageResponse {
    private List<EmployeeResponse> employees;
    private int pageNumber;
    private int totalEmployees;
}