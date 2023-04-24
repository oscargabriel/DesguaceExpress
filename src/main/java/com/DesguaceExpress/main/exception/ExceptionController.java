package com.DesguaceExpress.main.exception;

import com.DesguaceExpress.main.exception.custom.NoMemberInTheParking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoMemberInTheParking.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoMemberInTheParking exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason","No existe un socio registrado en el parqueadero "+exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(data);
    }
}
