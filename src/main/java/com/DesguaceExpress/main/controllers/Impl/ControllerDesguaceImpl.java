package com.DesguaceExpress.main.controllers.Impl;

import com.DesguaceExpress.main.controllers.ControllerDesguace;
import com.DesguaceExpress.main.dto.LogIn;
import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.services.Impl.ServiceDesguaceImpl;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class ControllerDesguaceImpl implements ControllerDesguace {

    Logger logger = LoggerFactory.getLogger(ControllerDesguaceImpl.class);

    ServiceDesguace serviceDesguace;



    public ControllerDesguaceImpl(ServiceDesguaceImpl serviceDesguace) {
        this.serviceDesguace = serviceDesguace;
    }


    @Override
    @GetMapping("top10Vehicle")
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParking() {
        return ResponseEntity.ok().body(serviceDesguace.TopVehicleInParking());
    }

    @Override
    @PostMapping("vehiculo/registrarEntrada")
    public ResponseEntity<HashMap<String, Long>> RegistrarEntrada(@RequestBody LogIn logIn) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                serviceDesguace.RegistrarEntrada(logIn.getLicencePlate(),logIn.getIdParking())
        );
    }

    @Override
    @PostMapping("socios/register")
    public ResponseEntity<String> CrearSocio(@RequestBody Members members) {
        return ResponseEntity.ok().body(serviceDesguace.crearSocio(members));
    }


}
