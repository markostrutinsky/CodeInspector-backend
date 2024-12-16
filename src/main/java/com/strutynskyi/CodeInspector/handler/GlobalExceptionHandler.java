package com.strutynskyi.CodeInspector.handler;


import com.strutynskyi.CodeInspector.responces.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Map<Class<? extends Exception>, HttpStatus> exceptionStatusMap = Map.of(
            IllegalArgumentException.class, HttpStatus.BAD_REQUEST,
            IOException.class, HttpStatus.BAD_REQUEST
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {

        ExceptionResponse errorObject = new ExceptionResponse();
        errorObject.setMessage(ex.getMessage());

        HttpStatus status = exceptionStatusMap.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        errorObject.setStatus(status);

        return new ResponseEntity<>(errorObject, status);
    }
}
