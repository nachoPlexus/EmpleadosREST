package com.plexus.directory.common;

public class SqlConstants {

    public static final String COUNTALL_FORM_DEVICES = "SELECT COUNT(*) FROM devices ;";
    public static final String GET_DEVICE_BY_ID = "SELECT * FROM devices WHERE id = ?";
    public static final String GET_DEVICE_BY_ASSIGNED_ID = "SELECT * FROM devices WHERE assigned_to = ?";
    public static final String GET_DEVICE_BY_BRAND = "SELECT * FROM devices WHERE brand LIKE ? LIMIT ? OFFSET ?";
    public static final String INSERT_DEVICE = "INSERT INTO devices (serial_number, brand, os) VALUES (?, ?, ?)";
    public static final String UPDATE_DEVICE = "UPDATE devices SET serial_number = ?, surname = ?, os = ? WHERE id = ?";
    public static final String DELETE_DEVICE = "DELETE FROM devices where id=?";
    public static final String SELECT_FORM_DEVICES = "SELECT * FROM devices LIMIT ? OFFSET ?;";

    private SqlConstants(){}
}
