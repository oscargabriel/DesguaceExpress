package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * Excepcion cuando no hay miembos asociados al parqueadero y se intenta registrar una entrada
 */
public class NoMemberInTheParking extends ResponseStatusException {

    public NoMemberInTheParking(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
