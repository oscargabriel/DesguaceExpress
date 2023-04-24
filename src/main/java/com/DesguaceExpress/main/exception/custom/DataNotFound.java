package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;


/**
 * excepcion cuando no algun criterio de busqueda en la base de datos no da respuesta positiva
 */
public class DataNotFound extends ResponseStatusException {

    public DataNotFound(HttpStatusCode status, String reason) {
        super(status, "No se encontro "+ reason);
    }
}
