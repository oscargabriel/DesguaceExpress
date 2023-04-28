package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * excepcion cuando un dato unico suministrado al crear o modificar esta ocupado
 */
public class DataIsInUse extends ResponseStatusException {

    public DataIsInUse(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
