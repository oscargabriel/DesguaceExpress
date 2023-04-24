package com.DesguaceExpress.main.repositories.dao;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Vehicle;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

/**
 * interfaz para la clase repositories, recibe peticiones de services para hacer consultas a la base de datos
 * en uso y devuelve la informacion, esta implementacion es solo para consultar
 */
public interface RepositoryDesguace {

    public Long LocationID();
    public Long MembersID();
    public Long ParkingID();
    public Long VehicleID();
    public Long VehicleParkingID();

    //regresar los 10 vehículos que más veces se han registrado en los diferentes
    // parqueaderos y cuantas veces han sido
    public List<Top10VehicleInParking> TopVehicleInParking();




    public Vehicle findVehicleByLicensePlate(String licensePlate);


}
