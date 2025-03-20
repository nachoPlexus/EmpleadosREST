package com.plexus.directory.dao.impl;

import com.plexus.directory.common.Constants;
import com.plexus.directory.common.SqlConstants;
import com.plexus.directory.dao.DeviceRepository;
import com.plexus.directory.domain.Device;
import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.mapper.DeviceRowMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.plexus.directory.common.SqlConstants.*;

@Repository
@Profile("versionBase")
public class DeviceRepositoryImpl implements DeviceRepository {
    private final DeviceRowMapper rowMapper;

    public DeviceRepositoryImpl(DeviceRowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Device> getAll() {
        List<Device> devices = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(Constants.DBURL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SqlConstants.GETALL_FORM_DEVICES);

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
        
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(GET_DEVICE_BY_ID)) {

            stmt.setInt(1, deviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rowMapper.mapRow(rs, rs.getRow());

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return new Device();
    }

    @Override
    public List<Device> get(String brand) {
        List<Device> devices = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(GET_DEVICE_BY_BRAND)) {

            stmt.setString(1, brand);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                devices.add(rowMapper.mapRow(rs, rs.getRow()));

        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        
        return devices;
    }

    @Override
    public int save(Device device) {
        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(INSERT_DEVICE, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, device.getSerialNumber());
            stmt.setString(2, device.getBrand());
            stmt.setString(3, device.getOs());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DataBaseException("Failed to insert device, no rows affected.");
            }
            return 1;
        } catch (SQLException e) {
            throw new DataBaseException("Database error: " + e.getMessage());
        }
    }

    @Override
    public int update(Device device) {

        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(UPDATE_DEVICE)) {

            stmt.setInt(1,device.getId());
            stmt.setString(2, device.getSerialNumber());
            stmt.setString(3, device.getBrand());
            stmt.setString(4, device.getOs());


            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        
    }

    @Override
    public boolean delete(Device device) {

        try (Connection conn = DriverManager.getConnection(Constants.DBURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_DEVICE)
        ){

            stmt.setInt(1,device.getId());
            return stmt.executeUpdate() > 0;

        }catch (SQLException e){
            throw new DataBaseException(e.getMessage());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }
}
