package com.plexus.directory.domain.mapper;

import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DeviceRowMapper implements RowMapper<Device> {

    @Override
    public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Device(
                rs.getInt("id"),
                rs.getString("serial_number"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getString("os"),
                rs.getInt("assigned_to")
        );
    }
}