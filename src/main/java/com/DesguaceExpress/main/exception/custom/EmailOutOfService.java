package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * generar excepciones relacionadas con el microservicio de enviar email
 */
public class EmailOutOfService extends ResponseStatusException {

    public EmailOutOfService(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
