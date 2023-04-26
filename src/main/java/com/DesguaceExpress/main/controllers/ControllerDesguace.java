package com.DesguaceExpress.main.controllers;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Members;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

/**
 * interfas para la clase controllers encargada de recibir solicitudes http, verificacion sencilla de los datos
 * y llama a la interface de services para para que procese el contenido y una verificacion mas exaustiva
 */
public interface ControllerDesguace {

    /**
     * muestras los 10 vehiculos que mas se han registrado en todos los parqueaderos con codigo 200
     * @return Lista Top10VehicleInParking
     */
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParking();

    /**
     * registra la entrada de los vehiculos al parqueadero
     * @param tiket contiene la placa del vehiculo y el id de parqueadero
     * @return id del registro de la entrada con codigo 201
     */
    public ResponseEntity<HashMap<String,Long>> RegistrarEntrada(@RequestBody Tiket tiket);

    /**
     * registra la salida de un vehiculo del parqueadero en el que esta
     * @param tiket contiene placa del vehiculo y el id del parqueadero
     * @return mensaje "salida exitosa"
     */
    public ResponseEntity<HashMap<String,String>> RegistrarSalida(@RequestBody Tiket tiket);

    /**
     * Dado el nombre del parqueadero muestra todos los vehiculos que estan parqueados actualmente
     * @param parkingName String nombre del parqueadero
     * @return lista de tipo VehicleByParking
     */
    public ResponseEntity<List<VehicleByParking>> findVehiclesByParking(@RequestBody String parkingName);

    /**
     * dado un numero de documento busca muestra todos los vehiculos que estan en los parqueaderos
     * @param memberDocument String del numero de documento del socio
     * @return lista VehicleInParkingByMembers
     */
    public ResponseEntity<List<VehicleInParkingByMembers>> findVehiclesByMember(@RequestBody String memberDocument);

    /**
     * dado el id de un vehiculo por url llama a services y muestra los detalles de ese vehiculo
     * @param id Long del vehiculo pK
     * @return VehicleDetails
     */
    public ResponseEntity<VehicleDetails> findVehicleDetailsById(@PathVariable Long id);


    public ResponseEntity<HashMap<String, String>> callSendEmail(@RequestBody EmailBodyPre emailBodySend);


    public ResponseEntity<String> CrearSocio(@RequestBody Members members);

}
