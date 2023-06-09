package com.DesguaceExpress.main.services;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Location;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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

    /**
     * Registra la salida de un vehiculo que este en el parqueadero por el que entro, si no encuentra la entrada
     * en los registros o si el parqueadero ingresado es incorrecto genera un throw y el se encarga de enviar
     * la informacion correspondiente
     * @param licensePlate String
     * @param idParking Long
     * @return mensaje indicando la salida exitosa
     */
    public HashMap<String,String> RegistrarSalida(String licensePlate, Long idParking);

    /**
     * Dado un nombre de parqueadero busca todos los vehiculos que se encuentren con un tiket abierto
     * @param parking String nombre del parqueadero
     * @return lista de VehicleByParking
     */
    public List<VehicleByParking> findVehiclesByParking(@RequestBody String parking);

    /**
     * recibe de controller un Documento y busca hace la peticion a repository para buscar todos los vehiculos
     * asociados
     * @param memberDocument String del Documento del socio
     * @return lista VehicleInParkingByMembers
     */
    public List<VehicleInParkingByMembers> findVehiclesByMember(@RequestBody String memberDocument);

    /**
     * recibe el id de un vehiculo de controller y llama a repository
     * @param id Long del vehiculo
     * @return VehicleDetails
     */
    public VehicleDetails findVehicleDetailsById(Long id);


    /**
     * dado un emailBodyPre llama a repository para verificar la informacion, si es correcta llama a
     * ServiceSendEmail y le da un emailBodySend para que envie el mensaje y retorna a controller el mensaje
     * que llega
     * @param emailBodyPre placa, email, mensaje, parqueaderoId
     * @return mensaje HashMap< String, String>
     */
    public HashMap<String, String> callSendEmail(EmailBodyPre emailBodyPre);

    /**
     * busca los 10 vehiculos que mas veces se han parqueado en un parqueadero dado su id
     * @param id Long parqueadero
     * @return List Top10VehicleInParking
     */
    public List<Top10VehicleInParking> TopVehicleInParkingByParkingId(Long id);

    /**
     * busca todos los vehiculos que se encuentren en los parqueaderos y dice si es la primera vez
     * o si ya han estando en parqueaderos previamente
     * @return Lista VehicleDetails
     */
    public List<VehicleDetails> VehicleInParkingForTheFirstTime();

    /**
     * dado el id de un parking genera un reporte de los ingresos generados en ese dia, esa semana, ese mes
     * y ese año
     * @param id Long parqueadero
     * @return PeriodicEarnings
     */
    public PeriodicEarnings findPeriodicEarningsByParkingId(Long id);

    /**
     * dado el id de un parqueadero busca el dia que genero mayor ganancia
     * @param id Long parqueadero
     * @return MaximumIncome ganancia y el dia
     */
    public MaximumIncome MaximumIncomeForDay(Long id);

    /**
     * busca los 3 parqueaderon que mas ingresos han tenido este año
     * @return Top3Parking
     */
    public List<Top3Parking> Top3ParkingThisYear();

    /**
     * busca los vehiculos en un parqueadero dado el id del parking que es obligatorio y pamatros opcionales
     * la fecha de inicio y fin estan encadenadas, segun los datos que hay llama a repository y para que
     * que procese segun con los datos que hay mediante una funcion sobrecargada
     * @param partialData Long parkingId; String partialLicensePlate(O); String dateInit(O) String dateEnd(O);
     * @return lista VehicleByParking
     */
    public List<VehicleByParking> VehiclesInAParkingByPartialData(PartialData partialData);

    /**
     * vincular un socio a un parqueadero, si el parqueadero esta sin socio no se pueden ingresar vegiculos
     * @param membertoparking SocioId, Parking Id
     * @return mensaje positivo si los datos son correctos
     */
    public HashMap<String, String> LinMemberToParking(MemberToParking membertoparking);

    /**
     * desvincula el socio que pueda tener un parqueadero, si no hay socios devuelve un throw informando que
     * no hay socios vinculados
     * @param parkingId Long parking
     * @return mensaje positivo si los datos son correctos
     */
    public HashMap<String, String> disconnectMemberToParking(Long parkingId);


    public HashMap<String, String> RegisterMember(@RequestBody Members members);

    public HashMap<String, String> UpdateMember(@RequestBody Members members);

    public HashMap<String, String> DeleteMember(@RequestBody Long id);

    public HashMap<String, String> RegisterVehicle(Vehicle vehicle);

    public HashMap<String, String> DeleteVehicle(Long id);

    public HashMap<String, String> RegisterParking(Parking parking);

    public HashMap<String, String> UpdateParking(Parking parking);

    public HashMap<String, String> DeleteParking(Long id);

    public HashMap<String, String> RegisterLocation(Location location);
}
