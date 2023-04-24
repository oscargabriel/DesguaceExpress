package com.DesguaceExpress.main.repositories.dao;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.entities.VehicleParking;
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

    /**
     * busca los 10 vehiculos que mas han registrado entradas en la tabla VehicleParking y los retorna a service
     * @return lista tipo Top10VehicleInParking
     */
    public List<Top10VehicleInParking> TopVehicleInParking();


    /**
     * busca un vehiculo en la bd dado una placa, si no la encuentra genera una excepcion y ExcepcionController
     * envia el mensaje de regreso
     * @param licensePlate string con la placa
     * @return vehiculo
     */
    public Vehicle findVehicleByLicensePlate(String licensePlate);

    /**
     * busca un parqueadero en la bd dado una id, si no la encuentra genera una excepcion y ExcepcionController
     * envia el mensaje de regreso
     * @param id Long del parqueadero
     * @return parqueadero
     */
    public Parking findParkingById(Long id);


    public VehicleParking findRegisterOpenByLicencePlate(String licensePlate);

}
