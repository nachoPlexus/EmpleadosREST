package com.plexus.directory.domain.error;

import static com.plexus.directory.common.Constants.DB_COMMUNICATION_ERROR;

public class DataBaseException extends RuntimeException {


    public DataBaseException(String message) {
        super(DB_COMMUNICATION_ERROR +message);
    }
}
