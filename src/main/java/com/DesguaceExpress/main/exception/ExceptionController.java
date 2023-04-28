package com.DesguaceExpress.main.exception;

import com.DesguaceExpress.main.exception.custom.*;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * atrapa las excepciones personalizadas y estructura el mensaje que se envia de respuesta por http
 */

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoMemberInTheParking.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Map<String, Object>> headleNoMemberInTheParking(NoMemberInTheParking exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason","No existe un socio registrado en el parqueadero "+exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(data);
    }

    @ExceptionHandler(DataNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> headDataNotFound(DataNotFound exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(VehicleRegistryIsBad.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> headVehicleRegistryIsOpen(VehicleRegistryIsBad exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

    @ExceptionHandler(EmailOutOfService.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> headEmailOutOfService(EmailOutOfService exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(InvalidExpressionException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Map<String, Object>> headInvalidExpressionException(InvalidExpressionException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(data);
    }

    @ExceptionHandler(DataIsInUse.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, Object>> headDataIsInUse(DataIsInUse exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> headPropertyValueException(DataIsInUse exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason","problemas de coneccion");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }
}
