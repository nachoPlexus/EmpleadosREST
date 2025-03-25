package com.plexus.directory.dao.impl;

import com.plexus.directory.common.Constants;
import com.plexus.directory.common.SqlConstants;
import com.plexus.directory.domain.Employee;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.mapper.EmployeeRowMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.plexus.directory.common.SqlConstants.*;

@Repository
@Profile("versionBase")
public class EmployeeRepositoryImpl implements com.plexus.directory.dao.EmployeeRepository {

    private final EmployeeRowMapper rowMapper;

    public EmployeeRepositoryImpl(EmployeeRowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }


    @Override
    public List<Employee> getAll(int page, int size) {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Constants.DBURL)) {
            PreparedStatement stmt = conn.prepareStatement(SqlConstants.SELECT_FORM_EMLOYEES);

            stmt.setInt(1, size);
            stmt.setInt(2, page * size);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(rowMapper.mapRow(rs, rs.getRow()));
            }

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee get(int employeeId) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYEE_BY_ID)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rowMapper.mapRow(rs, rs.getRow());

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return new Employee();
    }

    @Override
    public List<Employee> get(String employeeName, int page, int size) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYEE_BY_NAME)) {

            stmt.setString(1, "%"+employeeName+"%");
            stmt.setInt(2, size);
            stmt.setInt(3, page * size);

            ResultSet rs = stmt.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (rs.next())
                employees.add(rowMapper.mapRow(rs, rs.getRow()));
            return employees;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public int save(Employee employee) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSurname());
            stmt.setString(3, employee.getMailPlexus());
            stmt.setString(4, employee.getMailClient());
            stmt.setString(5, employee.getClientId());
            stmt.setString(6, employee.getPhoneNumber());
            stmt.setString(7, employee.getPhoneSerialNumber());


            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DataBaseException("Failed to insert employee, no rows affected.");
            }
            return 1;
        } catch (SQLException e) {
            throw new DataBaseException("Database error: " + e.getMessage());
        }
    }


    @Override
    public int update(Employee employee) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(UPDATE_EMPLOYEE)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSurname());
            stmt.setString(3, employee.getMailPlexus());
            stmt.setInt(4, employee.getId());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Employee employee) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_EMPLOYEE)
        ) {

            stmt.setInt(1, employee.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public int countAll() {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(COUNTALL_EMPLOYEES);
            return rs.next() ? rs.getInt(1) : -1;

        } catch (SQLException e) {
            throw new DataBaseException("error contando employees: "+e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException("error inesperado: "+ e.getMessage());
        }
    }
}
