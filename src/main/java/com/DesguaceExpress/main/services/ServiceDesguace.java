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

    /**
     * hace la solicitud a clase repository para buscar los 10 vehiculos que mas han registrado entradas y los retorna
     * a controller
     * @return Lista tipo Top10VehicleInParking
     */
    public List<Top10VehicleInParking> TopVehicleInParking();

    /**
     * Registra la entrada de un vehiculo dado su placa y el id del parqueadero buscandolos en la base de datos
     * si el parqueadero no tiene viculado un socio no se admite la entrada y se genera una excepcion y
     * ExceptionController se encarga de dar una respuesta
     * @param licensePlate String del vehiculo
     * @param idParking Long del parqueadero
     * @return HashMap con el Id de regristro de la entrada
     */
    public HashMap<String,Long> RegistrarEntrada(String licensePlate, Long idParking);



    public String crearSocio(Members members);
}
