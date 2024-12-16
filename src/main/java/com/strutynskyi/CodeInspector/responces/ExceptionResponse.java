package com.strutynskyi.CodeInspector.responces;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private HttpStatus status;
    private String message;
}
