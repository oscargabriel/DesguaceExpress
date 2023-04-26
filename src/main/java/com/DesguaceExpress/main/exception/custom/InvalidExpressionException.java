package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * exepcion cuando el analizador lexico encuentra discrepancias
 */
public class InvalidExpressionException extends ResponseStatusException {

    public InvalidExpressionException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
