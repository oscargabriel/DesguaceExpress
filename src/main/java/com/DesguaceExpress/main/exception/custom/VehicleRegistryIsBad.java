package com.DesguaceExpress.main.exception.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class VehicleRegistryIsBad extends ResponseStatusException {

    public VehicleRegistryIsBad(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
