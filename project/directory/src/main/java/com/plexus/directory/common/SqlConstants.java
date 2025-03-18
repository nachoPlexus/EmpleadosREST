package com.plexus.directory.common;


public class SqlConstants {
    private static final String TEST="TEST";
    public static final String SELECT_FORM_EMLOYEES = "Select * FROM employees;";
    public static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ?";
    public static final String GET_EMPLOYEE_BY_NAME = "SELECT * FROM employees WHERE name = ?";
    public static final String INSERT_EMPLOYEE = "INSERT INTO employees (name, surname, mail_plexus) VALUES (?, ?, ?)";
    public static final String UPDATE_EMPLOYEE = "UPDATE employees SET name = ?, surname = ?, mail_plexus = ? WHERE id = ?";
    public static final String DELETE_EMPLOYEE = "DELETE FROM employees where id=?";
    private SqlConstants(){}
}
