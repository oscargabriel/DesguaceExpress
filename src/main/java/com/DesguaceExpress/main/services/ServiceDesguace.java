package com.DesguaceExpress.main.services;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Members;
import org.springframework.web.bind.annotation.RequestBody;

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


    public List<Top10VehicleInParking> TopVehicleInParkingByParkingId(Long id);

    public String crearSocio(Members members);
}
