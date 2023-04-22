package com.DesguaceExpress.main.controllers.Impl;

import com.DesguaceExpress.main.controllers.ControllerDesguace;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.services.Impl.ServiceDesguaceImpl;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDesguaceImpl implements ControllerDesguace {

    Logger logger = LoggerFactory.getLogger(ControllerDesguaceImpl.class);

    ServiceDesguace serviceDesguace;

    public ControllerDesguaceImpl(ServiceDesguaceImpl serviceDesguace) {
        this.serviceDesguace = serviceDesguace;
    }

    @Override
    @PostMapping("Associate/register")
    public ResponseEntity<String> CrearSocio(@RequestBody Members associate) {
        return ResponseEntity.ok().body("se registro");
    }
}
