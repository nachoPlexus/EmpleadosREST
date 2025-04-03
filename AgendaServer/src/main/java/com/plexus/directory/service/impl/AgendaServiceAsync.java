package com.plexus.directory.service.impl;

import org.springframework.stereotype.Service;

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
