package io.piano.accesscontrol.web;

import io.piano.accesscontrol.exception.NoSuchKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessControlExceptionHandler {
    @ExceptionHandler(value = NoSuchKeyException.class)
    public ResponseEntity<String> handleNoSuchKey(NoSuchKeyException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
