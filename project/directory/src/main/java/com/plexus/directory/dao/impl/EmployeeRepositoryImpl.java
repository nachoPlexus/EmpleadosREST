package com.plexus.directory.dao.impl;

import com.plexus.directory.common.Constants;
import com.plexus.directory.common.SqlConstants;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.plexus.directory.common.SqlConstants.GET_EMPLOYEE_BY_ID;
import static com.plexus.directory.common.SqlConstants.GET_EMPLOYEE_BY_NAME;
import static com.plexus.directory.common.SqlConstants.INSERT_EMPLOYEE;
import static com.plexus.directory.common.SqlConstants.UPDATE_EMPLOYEE;
import static com.plexus.directory.common.SqlConstants.DELETE_EMPLOYEE;

@Repository
@Profile("versionBase")
public class EmployeeRepositoryImpl implements com.plexus.directory.dao.EmployeeRepository {


    @Override
    public List<Employee> getAll() {
        List<Employee> employees=new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Constants.DBURL)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SqlConstants.SELECT_FORM_EMLOYEES);


            while (rs.next()){
                employees.add(new Employee(rs));
            }

        }catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee get(int employeeId) {

        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
            PreparedStatement stmt =conn.prepareStatement(GET_EMPLOYEE_BY_ID)){

            stmt.setInt(1,employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return new Employee(rs);

        }catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    @Override
    public Employee get(String employeeName) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(GET_EMPLOYEE_BY_NAME)) {

            stmt.setString(1,employeeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Employee(rs);

        }catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    @Override
    public int save(Employee employee) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSurname());
            stmt.setString(3, employee.getMailPlexus());

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
        ){

            stmt.setInt(1,employee.getId());
            return stmt.executeUpdate() > 0;

        }catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
