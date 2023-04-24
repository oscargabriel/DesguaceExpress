package com.DesguaceExpress.main.controllers;

import com.DesguaceExpress.main.dto.LogIn;
import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Members;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

/**
 * interfas para la clase controllers encargada de recibir solicitudes http, verificacion sencilla de los datos
 * y llama a la interface de services para para que procese el contenido y una verificacion mas exaustiva
 */
public interface ControllerDesguace {

    /**
     * muestras los 10 vehiculos que mas se han registrado en todos los parqueaderos
     * @return Lista Top10VehicleInParking
     */
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParking();


    public ResponseEntity<HashMap<String,Long>> RegistrarEntrada(@RequestBody LogIn logIn);

    public ResponseEntity<String> CrearSocio(@RequestBody Members members);

}
