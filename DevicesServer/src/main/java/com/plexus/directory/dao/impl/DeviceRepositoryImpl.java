package com.plexus.directory.dao.impl;

import com.plexus.directory.common.Constants;
import com.plexus.directory.common.SqlConstants;
import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.error.StatusException;
import com.plexus.directory.domain.mapper.DeviceRowMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.plexus.directory.common.Constants.*;
import static com.plexus.directory.common.SqlConstants.*;

@Repository
@Profile("versionBase")
public class DeviceRepositoryImpl implements DeviceRepository {
    private final DeviceRowMapper rowMapper;

    public DeviceRepositoryImpl(DeviceRowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Device> getAll(int page, int size) {
        List<Device> devices = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Constants.DBURL); PreparedStatement stmt = conn.prepareStatement(SqlConstants.SELECT_FORM_DEVICES)) {

            stmt.setInt(1, size);
            stmt.setInt(2, page * size);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                devices.add(rowMapper.mapRow(rs, rs.getRow()));
            }

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return devices;
    }

    @Override
    public Device get(int deviceId) {

        try (Connection conn = DriverManager.getConnection(DBURL); PreparedStatement stmt = conn.prepareStatement(GET_DEVICE_BY_ID)) {
            stmt.setInt(1, deviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rowMapper.mapRow(rs, rs.getRow());
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return new Device();
    }

    @Override
    public List<Device> get(String brand, int resolvedPage, int resolvedSize) {
        List<Device> devices = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DBURL); PreparedStatement stmt = conn.prepareStatement(GET_DEVICE_BY_BRAND)) {

            stmt.setString(1, "%" + brand + "%");
            stmt.setInt(2, resolvedSize);
            stmt.setInt(3, resolvedPage * resolvedSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) devices.add(rowMapper.mapRow(rs, rs.getRow()));

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return devices;
    }

    @Override
    public Device getByAssignated(int employeeId) {
        try (Connection conn = DriverManager.getConnection(DBURL); PreparedStatement stmt = conn.prepareStatement(GET_DEVICE_BY_ASSIGNED_ID)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rowMapper.mapRow(rs, rs.getRow());
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return new Device();
    }

    @Override
    public int countAll() {

        try (Connection conn = DriverManager.getConnection(DBURL); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNTALL_FORM_DEVICES);

            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    //CONSULTAS MASIVAS

    @Override
    public int save(List<Device> devices) {
        if (!devices.isEmpty()) {
            StringBuilder sql = buildConsultaAdd(devices);

            try (Connection conn = DriverManager.getConnection(Constants.DBURL); Statement stmt = conn.createStatement()) {
                return stmt.executeUpdate(sql.toString());
            } catch (SQLException e) {
                if (e.getMessage().contains("UNIQUE")) {
                    throw new StatusException(Map.of("Error de constraint", "Hay campos que tienen que ser unicos y no lo son", "Detalle SQL", e.getMessage(), "Consejo", "verifica los devices que has enviados, comprueba que los datos los has introducido bien"));
                }
                throw new StatusException(Map.of("Error en la inserción masiva", e.getMessage()));
            }
        }
        return 0;
    }

    @Override
    public int update(List<Device> devices) {
        if (!devices.isEmpty()) {
            StringBuilder sql = buildConsultaUpdate(devices);

            try (Connection conn = DriverManager.getConnection(Constants.DBURL);
                 Statement stmt = conn.createStatement()) {
                return stmt.executeUpdate(sql.toString());
            } catch (SQLException e) {
                if (e.getMessage().contains("UNIQUE")) {
                    throw new StatusException(Map.of("Error de constraint", "Hay campos que tienen que ser unicos y no lo son", "Detalle SQL", e.getMessage(), "Consejo", "verifica los devices que has enviados, comprueba que los datos los has introducido bien"));
                }
                throw new StatusException(Map.of("Error en la update masiva", e.getMessage()));
            }catch (Exception e){
                throw new DataBaseException("error inesperado, detalles"+e);
            }
        }
        return 0;
    }

    @Override
    public int delete(List<Device> devices) {
        if (!devices.isEmpty()) {
            StringBuilder sql = buildConsultaDelete(devices);

            try (Connection conn = DriverManager.getConnection(Constants.DBURL); Statement stmt = conn.createStatement()) {
                return stmt.executeUpdate(sql.toString());
            } catch (SQLException e) {
                throw new StatusException(Map.of("Error en el borrado masivo", e.getMessage()));
            }
        }
        return 0;
    }


    private StringBuilder buildConsultaAdd(List<Device> devices) {
        StringBuilder sql = new StringBuilder("INSERT INTO devices (serial_number, brand, model, os, assigned_to) VALUES ");

        try {
            for (Device device : devices) {
                sql.append("(")
                        .append("'").append(device.getSerialNumber().replace("'", "''")).append("', ")//comillas bien por si acaso

                        .append(device.getBrand() != null
                                ? "'" + device.getBrand().replace("'", "''") + "'"
                                : "NULL")
                        .append(", ")

                        .append(device.getModel() != null
                                ? "'" + device.getModel().replace("'", "''") + "'"
                                : "NULL")
                        .append(", ")

                        .append("'").append(device.getOs().replace("'", "''")).append("', ")

                        .append(device.getAssignedTo() != 0 ? device.getAssignedTo() : "NULL")

                        .append("), ");
            }

            sql.setLength(sql.length() - 2); //quito coma
            sql.append(";");

        } catch (Exception e) {
            throw new StatusException(Map.of("Building Query error", "Error al generar la consulta de add masiva", "Detalles", e.getMessage()));
        }
        return sql;
    }

    private StringBuilder buildConsultaUpdate(List<Device> devices) {
        StringBuilder sql = new StringBuilder("UPDATE devices SET ");
        try {
            String[] strings = {"brand", "model", "os"};
            for (String columna : strings) {
                sql.append(columna).append(" = CASE ");
                for (Device device : devices) {
                    sql.append("WHEN serial_number = '").append(device.getSerialNumber()).append("' THEN '").append(device.getByNombreFila(columna)).append("' ");
                }
                sql.append("END, ");
            }

            String[] enteros = {"assigned_to"};
            for (String column : enteros) {
                sql.append(column).append(" = CASE ");
                for (Device device : devices) {
                    int value = device.getAssignedTo();
                    sql.append("WHEN serial_number = '").append(device.getSerialNumber()).append("' THEN ").append(value != 0 ? value : "NULL").append(" ");
                }
                sql.append("END, ");
            }

            sql.setLength(sql.length() - 2); //quito coma
            sql.append(" WHERE serial_number IN ('");
            devices.forEach(device -> sql.append(device.getSerialNumber()).append(", "));
            sql.setLength(sql.length() - 2); //quito coma
            sql.append("')");
        } catch (Exception e) {
            throw new StatusException(Map.of("Building Query error", "Error al generar la consulta masiva para eliminar a todos los devices", "Dettalles", e.getMessage()));
        }
        return sql;
    }

    private StringBuilder buildConsultaDelete(List<Device> devices) {
        StringBuilder sql = new StringBuilder("DELETE FROM devices WHERE id IN (");
        devices.forEach(d -> sql.append(d.getId()).append(", "));
        sql.setLength(sql.length() - 2); //quito coma
        sql.append(");");
        return sql;
    }

}
