package com.DesguaceExpress.main.repositories.dao;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.dto.VehicleByParking;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.entities.VehicleParking;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Member;
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

    /**
     * busca un parqueadero en la bd dado una id, si no la encuentra genera una excepcion y ExcepcionController
     * envia el mensaje de regreso
     * @param parkingName String del nombre del parqueadero
     * @return parqueadero
     */
    public Parking findParkingByName(String parkingName);

    /**
     * apartir de una placa busca si existe registro abierto, si existe devuelve el registro, si no existe devuelve
     * null
     * @param licensePlate String Placa
     * @return el registro si existe o null si no existe
     */
    public VehicleParking findRegisterOpenByLicencePlate(String licensePlate);

    /**
     * busca los vehiculos que esten asociados a un parking con el tiket abierto
     * @param id Long del parqueadero
     * @return lista VehicleByParking
     */
    public List<VehicleByParking> findVehicleByParkingId(Long id);

    public Member findMemberByName(String memberName);

}
