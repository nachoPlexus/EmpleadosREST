package com.plexus.directory.web;

import com.plexus.directory.domain.error.BadRequestException;
import com.plexus.directory.domain.error.DataBaseException;
import com.plexus.directory.domain.error.NoContentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)    // 400
    public Map<String, String> handleBadRequest(BadRequestException e) {
        return Map.of("error", e.getMessage());
    }


    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)    // 204
    public void handleNoContentException(NoContentException e) {
        // Sin body
    }


    @ExceptionHandler(DataBaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)    // 500
    public Map<String, String> handleDataBaseException(DataBaseException e) {
        return Map.of("error", e.getMessage());
    }

}
