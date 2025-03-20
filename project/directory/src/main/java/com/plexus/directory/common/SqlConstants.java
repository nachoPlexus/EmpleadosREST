package com.plexus.directory.common;


public class SqlConstants {
    public static final String GETALL_FORM_DEVICES = "SELECT * FROM devices;";
    public static final String SELECT_FORM_EMLOYEES = "Select * FROM employees;";
    public static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ?";
    public static final String GET_DEVICE_BY_ID = "SELECT * FROM devices WHERE id = ?";
    public static final String GET_DEVICE_BY_BRAND = "SELECT * FROM devices WHERE brand = ?";
    public static final String GET_EMPLOYEE_BY_NAME = "SELECT * FROM employees WHERE name LIKE ?";
    public static final String INSERT_EMPLOYEE = "INSERT INTO employees (name, surname, mail_plexus) VALUES (?, ?, ?)";
    public static final String INSERT_DEVICE = "INSERT INTO devices (serial_number, brand, os) VALUES (?, ?, ?)";
    public static final String UPDATE_EMPLOYEE = "UPDATE employees SET name = ?, surname = ?, mail_plexus = ? WHERE id = ?";
    public static final String UPDATE_DEVICE = "UPDATE devices SET serial_number = ?, surname = ?, os = ? WHERE id = ?";
    public static final String DELETE_EMPLOYEE = "DELETE FROM employees where id=?";
    public static final String DELETE_DEVICE = "DELETE FROM devices where id=?";
    private SqlConstants(){}
}
