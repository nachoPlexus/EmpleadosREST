package com.plexus.directory.service.impl;

import com.plexus.directory.domain.model.DeviceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AgendaServiceAsync {

    private final AgendaServiceImpl service;

    public AgendaServiceAsync(AgendaServiceImpl service) {
        this.service = service;
    }

//    @Async
//    public CompletableFuture<Integer> getDevicesCountAsync() {
//        return CompletableFuture.completedFuture(service.countAll());
//    }
//    @Async
//    public CompletableFuture<DeviceDto> getAssignatedDevicesAsync() {
//        return CompletableFuture.completedFuture(service.getAssignatedDevicesAsync());
//    }
}
