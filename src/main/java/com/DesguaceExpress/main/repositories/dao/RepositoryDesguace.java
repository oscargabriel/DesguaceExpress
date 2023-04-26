package com.DesguaceExpress.main.repositories.dao;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.dto.VehicleByParking;
import com.DesguaceExpress.main.dto.VehicleDetails;
import com.DesguaceExpress.main.dto.VehicleInParkingByMembers;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.entities.VehicleParking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

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
    public List<VehicleByParking> findVehicleByParkingId(Long id, String name);

    /**
     * recibe de Services un Documento y busca se existe, si no encuentra genera un throw
     * @param memberDocument String
     * @return Members
     */
    public Members findMemberByDocument(String memberDocument);


    /**
     * recibe de Services un id y nombre de un socio busca los vehiculos que esten parqueados si no encuentra
     * genera una excepcion
     * @param id Long del usuario
     * @param name String de nombre y apellido
     * @return lista VehicleInParkingByMembers
     */
    public List<VehicleInParkingByMembers> VehicleInParkingByMembers(Long id, String name);

    /**
     * busca un vehiculo por su id, si existe lo regresa, si no exite genera un throw
     * @param id Long del vehiculo
     * @return Vehiculo
     */
    public Vehicle findVehicleById(Long id);

    /**
     * busca los detalles de un vehiculo que se encuentra en un parqueadero, si lo encuentra lo regresa, si no
     * genera un throw
     * @param id id del vehiculo
     * @return VehicleDetails
     */
    public VehicleDetails findVehicleDetailsById(Long id);



}
