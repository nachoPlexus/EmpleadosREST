package com.plexus.directory.dao.retrofit.config;

import com.plexus.directory.dao.retrofit.llamadas.DevicesApi;
import com.squareup.moshi.Moshi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Configuration
public class DevicesApiConfig {

    @Value("${api.devices.base-url}")
    private String baseUrl;

    @Bean
    public Retrofit devicesRetrofit(OkHttpClient okHttpClient, Moshi moshi) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    @Bean
    public DevicesApi devicesApi(Retrofit devicesRetrofit) {
        return devicesRetrofit.create(DevicesApi.class);
    }
}