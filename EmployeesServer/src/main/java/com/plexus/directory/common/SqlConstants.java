package com.plexus.directory.common;

public class SqlConstants {

    public static final String COUNTALL_EMPLOYEES = "SELECT COUNT(*) FROM employees";
    public static final String SELECT_FORM_EMLOYEES = "SELECT * FROM employees LIMIT ? OFFSET ?;";
    public static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ?";
    public static final String GET_EMPLOYEE_BY_NAME = "SELECT * FROM employees WHERE name LIKE ? LIMIT ? OFFSET ?";
    public static final String INSERT_EMPLOYEE = "INSERT INTO employees (name, surname, mail_plexus, mail_client, client_id, phone_number, phone_serial_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_EMPLOYEE = "UPDATE employees SET name = ?, surname = ?, mail_plexus = ? WHERE id = ?";
    public static final String DELETE_EMPLOYEE = "DELETE FROM employees where id=?";
    public static final String GETALL_EMPLOYEES_ID_SURNAME = "SELECT id, surname FROM employees";
    public static final String UPDATE_EMPLOYEE_SURNAME = "UPDATE employees SET surname = ? WHERE id = ?";

    private SqlConstants(){}
}
