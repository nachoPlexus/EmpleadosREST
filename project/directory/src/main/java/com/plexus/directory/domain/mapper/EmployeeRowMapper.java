package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("mail_plexus"),
                rs.getString("mail_client"),
                rs.getString("client_id"),
                rs.getString("phone_number"),
                rs.getString("phone_serial_number")
        );
    }
}
