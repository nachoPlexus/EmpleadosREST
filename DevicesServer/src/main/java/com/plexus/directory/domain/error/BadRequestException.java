package com.plexus.directory.domain.error;

import static com.plexus.directory.common.Constants.INVALID_REQUEST;

public class BadRequestException extends RuntimeException {


    public BadRequestException(String message) {
        super(INVALID_REQUEST + message);
    }
}
