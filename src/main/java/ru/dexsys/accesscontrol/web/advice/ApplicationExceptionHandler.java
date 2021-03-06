package ru.dexsys.accesscontrol.web.advice;

import ru.dexsys.accesscontrol.exception.CheckProcessException;
import ru.dexsys.accesscontrol.exception.NoSuchKeyException;
import ru.dexsys.accesscontrol.exception.NoSuchRoomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(value = {CheckProcessException.class, NoSuchKeyException.class, NoSuchRoomException.class})
    public ResponseEntity<String> handleNoSuchValueException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<String> dataBaseExceptionHandler(SQLException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something wrong with application storage. Please, call your DBA");
    }
}
