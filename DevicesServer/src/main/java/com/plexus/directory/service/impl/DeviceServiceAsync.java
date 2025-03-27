package com.plexus.directory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DeviceServiceAsync {

    private final DeviceServiceImpl service;

    @Autowired
    public DeviceServiceAsync(DeviceServiceImpl service) {
        this.service = service;
    }

    @Async
    public CompletableFuture<Integer> getDevicesCountAsync() {
        return CompletableFuture.completedFuture(service.countAll());
    }
}
