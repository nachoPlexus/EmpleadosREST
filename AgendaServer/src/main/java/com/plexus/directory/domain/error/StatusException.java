package com.plexus.directory.domain.error;

import java.util.Map;

public class StatusException extends RuntimeException {
    private final Map<String, Object> details;

    public StatusException(Map<String, Object> details) {
        this.details = details;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
