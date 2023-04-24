package com.DesguaceExpress.main.controllers;

import com.DesguaceExpress.main.dto.Tiket;
import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.dto.VehicleByParking;
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
     * Dado el id del parking muestra todas las entradas registradas
     * @param parking
     * @return
     */
    public ResponseEntity<List<VehicleByParking>> findVehiclesByParking(@RequestBody String parking);


    public ResponseEntity<String> CrearSocio(@RequestBody Members members);

}
