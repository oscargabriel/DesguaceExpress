package com.DesguaceExpress.main.services;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Members;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

/**
 * infartas para la clase services, recibe los datos desde controllers y hace una verificacion de los datos
 * llamando a la clase de repositories
 */
public interface ServiceDesguace {

    //regresar los 10 vehículos que más veces se han registrado en los diferentes
    // parqueaderos y cuantas veces han sido
    public List<Top10VehicleInParking> TopVehicleInParking();

    public HashMap<String,Long> RegistrarEntrada(String licensePlate, Long idParking);

    public String crearSocio(Members members);
}
