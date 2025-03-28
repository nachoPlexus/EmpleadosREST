package com.plexus.directory.dao.retrofit.llamadas;
import com.plexus.directory.domain.model.DeviceDto;
import com.plexus.directory.domain.model.DevicePageDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface DevicesApi {

    @GET("devices")
    Call<DevicePageDto> getDevices(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("devices/id/{deviceId}")
    Call<DeviceDto> getDeviceById(
            @Path("deviceId") int deviceId
    );

    @GET("devices/brand/{deviceBrand}")
    Call<DevicePageDto> getDevicesByBrand(
            @Path("deviceBrand") String deviceBrand,
            @Query("page") int page,
            @Query("size") int size
    );

    @POST("devices")
    Call<String> createDevices(
            @Body List<DeviceDto> devices
    );

    @PUT("devices")
    Call<String> updateDevices(
            @Body List<DeviceDto> devices
    );

    @HTTP(method = "DELETE", path = "devices", hasBody = true)
    Call<String> deleteDevices(
            @Body List<DeviceDto> deviceDtos
    );
}