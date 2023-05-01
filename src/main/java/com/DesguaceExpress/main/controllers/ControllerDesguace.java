package com.DesguaceExpress.main.controllers;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Location;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Dado el nombre del parqueadero muestra todos los vehiculos que estan parqueados actualmente
     * @param parkingName String nombre del parqueadero
     * @return lista de tipo VehicleByParking
     */
    public ResponseEntity<List<VehicleByParking>> findVehiclesByParking(String parkingName);

    /**
     * dado un numero de documento busca muestra todos los vehiculos que estan en los parqueaderos
     * @param memberDocument String del numero de documento del socio
     * @return lista VehicleInParkingByMembers
     */
    public ResponseEntity<List<VehicleInParkingByMembers>> findVehiclesByMember(String memberDocument);

    /**
     * dado el id de un vehiculo por url llama a services y muestra los detalles de ese vehiculo
     * @param id Long del vehiculo pK
     * @return VehicleDetails
     */
    public ResponseEntity<VehicleDetails> findVehicleDetailsById(@PathVariable Long id);

    /**
     * recibe desde http la solicitud para enviar un mensaje, llama a services y devuelve el mensaje que viene
     * desde services
     * @param emailBodySend placa, email, mensaje, parqueaderoId
     * @return mensaje HashMap< String, String>
     */
    public ResponseEntity<HashMap<String, String>> callSendEmail(EmailBodyPre emailBodySend);

    /**
     * busca los 10 vehiculos que mas veces se han parqueado en un parqueadero dado su id
     * @param id Long parqueadero
     * @return List Top10VehicleInParking
     */
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParkingByParkingId(Long id);

    /**
     * busca todos los vehiculos que se encuentren en los parqueaderos y dice si es la primera vez
     * o si ya han estando en parqueaderos previamente
     * @return Lista VehicleDetails
     */
    public ResponseEntity<List<VehicleDetails>> VehicleInParkingForTheFirstTime();

    /**
     * dado el id de un parking genera un reporte de los ingresos generados en ese dia, esa semana, ese mes
     * y ese año
     * @param id Long parqueadero
     * @return PeriodicEarnings
     */
    public ResponseEntity<PeriodicEarnings> findPeriodicEarningsByParkingId(Long id);

    /**
     * dado el id de un parqueadero busca el dia que genero mayor ganancia
     * @param id Long parqueadero
     * @return MaximumIncome ganancia y el dia
     */
    public ResponseEntity<MaximumIncome> MaximumIncomeForDay(Long id);

    /**
     * busca los 3 parqueaderon que mas ingresos han tenido este año
     * @return Top3Parking
     */
    public ResponseEntity<List<Top3Parking>> Top3ParkingThisYear();

    /**
     * busca los vehiculos en un parqueadero dado el id del parking que es obligatorio y pamatros opcionales
     * la fecha de inicio y fin estan encadenadas
     * @param partialData Long parkingId; String partialLicensePlate(O); String dateInit(O) String dateEnd(O);
     * @return lista VehicleByParking
     */
    public ResponseEntity<List<VehicleByParking>> VehiclesInAParkingByPartialData(PartialData partialData);

    public ResponseEntity<HashMap<String, String>> RegisterMember(Members members);


    public ResponseEntity<HashMap<String, String>> UpdateMember(Members members);


    public ResponseEntity<HashMap<String, String>> DeleteMember(Long id);


    public ResponseEntity<HashMap<String, String>> RegisterVehicle(Vehicle vehicle);


    public ResponseEntity<HashMap<String, String>> DeleteVehicle(Long id);

    /**
     * registra la entrada de los vehiculos al parqueadero
     * @param tiket contiene la placa del vehiculo y el id de parqueadero
     * @return id del registro de la entrada con codigo 201
     */
    public ResponseEntity<HashMap<String, Long>> RegistrarEntrada(Tiket tiket);

    /**
     * registra la salida de un vehiculo del parqueadero en el que esta
     * @param tiket contiene placa del vehiculo y el id del parqueadero
     * @return mensaje "salida exitosa"
     */
    public ResponseEntity<HashMap<String, String>> RegistrarSalida(Tiket tiket);


    public ResponseEntity<HashMap<String, String>> LinkMemberToParking(MemberToParking membertoparking);

    public ResponseEntity<HashMap<String, String>> disconnectMemberToParking(HashMap<String, Long>  parkingId);

    public ResponseEntity<HashMap<String, String>> RegisterParking(Parking parking);


    public ResponseEntity<HashMap<String, String>> UpdateParking(Parking parking);


    public ResponseEntity<HashMap<String, String>> DeleteParking(Long id);


    public ResponseEntity<HashMap<String, String>> RegisterLocation(Location location);






}
