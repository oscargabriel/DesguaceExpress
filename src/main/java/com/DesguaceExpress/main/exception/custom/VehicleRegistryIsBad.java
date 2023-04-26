package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * clase para administrar las excepciones al momento de registrar la salida o la entrada de un vehiculo
 * en un parqueadero
 */

public class VehicleRegistryIsBad extends ResponseStatusException {

    public VehicleRegistryIsBad(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
