package com.DesguaceExpress.main.exception;

import com.DesguaceExpress.main.exception.custom.DataNotFound;
import com.DesguaceExpress.main.exception.custom.NoMemberInTheParking;
import com.DesguaceExpress.main.exception.custom.VehicleRegistryIsBad;
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
    public ResponseEntity<Map<String, Object>> NoMemberInTheParking(NoMemberInTheParking exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason","No existe un socio registrado en el parqueadero "+exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(data);
    }

    @ExceptionHandler(DataNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> DataNotFound(DataNotFound exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(VehicleRegistryIsBad.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> VehicleRegistryIsOpen(VehicleRegistryIsBad exception){
        Map<String, Object> data = new HashMap<>();
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

}
