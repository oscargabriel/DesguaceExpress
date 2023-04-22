package com.DesguaceExpress.main.controllers;

import com.DesguaceExpress.main.entities.Members;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ControllerDesguace {
    public ResponseEntity<String> CrearSocio(@RequestBody Members associate);

}
