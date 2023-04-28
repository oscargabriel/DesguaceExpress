package com.DesguaceExpress.main.repositories.dao;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.*;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
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

    /**
     * recibe de Services un Documento y busca se existe, si no encuentra genera un throw
     * @param memberDocument String
     * @return Members
     */
    public Members findMemberByDocument(String memberDocument);

    /**
     * recibe de Services un id y busca se existe, si no encuentra genera un throw
     * @param id Long
     * @return Members
     */
    public Members findMemberById(Long id);
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

    /**
     * con una placa busca si existe un tiket abierto en un parqueadero y verifica que coincida la informacion
     * de ser positiva debuelve un EmailBodySend, caso cotrario devuelve un throw indicando la discrepancia
     * @param emailBodyPre busca que los datos conincidan
     * @return EmailBodySend
     */
    public EmailBodySend VehicleInParkingByLicensePlate(EmailBodyPre emailBodyPre);

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
     * dado un periodo con dos fechas y el id del parqueadero busca los ingresos que genero el parqueadero en
     * ese tiempo
     * @param parkingId Long del parqueadero
     * @param dateInit LocalDateTime desde donde se va a buscar
     * @param dateEnd LocalDateTime hasta que fecha se va a buscar
     * @return Double la suma de los ingresos ese dia
     */
    public Double FindEarningsByDate(Long parkingId, LocalDateTime dateInit, LocalDateTime dateEnd);

    /**
     * dado el id de un parqueadero busca el dia que genero mayor ganancia
     * @param id Long parqueadero
     * @return MaximumIncome ganancia y el dia
     */
    public MaximumIncome MaximumIncomeForDay(Long id);

    /**
     * busca los 3 parqueaderon que mas ingresos han tenido este año
     * @param dateInit fecha inicial
     * @param dateEnd fecha actual
     * @return Top3Parking
     */
    public List<Top3Parking> Top3ParkingThisYear(LocalDateTime dateInit, LocalDateTime dateEnd);

    /**
     * funcion sobrecargada, recibe un id de parqueadero y buscar los vehiculos que hay en el, los ordena por
     * fecha de ingreso
     * @param id ParkingId
     * @return lista de VehicleByParking
     */
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id);

    /**
     * funcion sobrecargada, recibe un id de parqueadero y una placa parcial o total
     * y buscar los vehiculos que hay en el, los ordena por fecha de ingreso
     * @param id ParkingId
     * @param licensePlate String placa parcial o total
     * @return lista de VehicleByParking
     */
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id,String licensePlate);

    /**
     * funcion sobrecargada, recibe un id de parqueadero y una fecha inicial y final
     * y buscar los vehiculos que hay en el, los ordena por fecha de ingreso
     * @param id Long ParkingId
     * @param dateInit LocalDateTime
     * @param dateEnd LocalDateTime
     * @return lista de VehicleByParking
     */
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id,LocalDateTime dateInit, LocalDateTime dateEnd);

    /**
     * funcion sobrecargada, recibe un id de parqueadero, una placa parcial o total  y una fecha inicial y final
     * y buscar los vehiculos que hay en el, los ordena por fecha de ingreso
     * @param id Long ParkingId
     * @param licensePlate String placa parcial o total
     * @param dateInit LocalDateTime
     * @param dateEnd LocalDateTime
     * @return
     */
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id,String licensePlate, LocalDateTime dateInit, LocalDateTime dateEnd);

    /**
     * funcion para uso de la funcion sobrecargada, recibe una query y procesa los datos para retornar
     * una lista de vehiculos, si la lista esta vacia genera un throw para indicar que no se encuntraron
     * resultados
     * @param query Object[]
     * @param id Long ParqueaderoId
     * @return lista VehicleByParking
     */
    public List<VehicleByParking> QueryDataPartial(TypedQuery<Object[]> query, Long id);

    /**
     * busca si un parqueadero tiene socios viculados
     * @param id Long ParkingId
     * @return Boolean true si hay y false si no hay
     */
    public Boolean FindMemberInParking(Long id);

    /**
     * buscar todos los vehiculos que le pertenecen a un socio
     * @param id Long del socio
     * @return lista con los id de los vehiculos
     */
    public List<Long> FindVehicleIdByMemberId(Long id);

    /**
     * buscar todos los vehiculos que le pertenecen a un socio
     * @param id Long del socio
     * @return lista con los id de los vehiculos
     */
    public List<Long> FindVehicleParkingIdByMemberId(Long id);

    public Vehicle FindVehicleById(Long id);

    public Location FindLocationById(Long id);

    public Parking FindParkingById(Long id);


    public Boolean FindIfLicensePlateIsInUse(String licensePlate, Long id);

    public Boolean FindIfDocumentIsInUse(String document, Long id);

    public Boolean FindIfEmailIsInUse(String email, Long id);

    public Boolean FindIfPhoneIsInUse(Long phone, Long id);

    public Boolean FindIfParkingNameIsInUse(String parkingName, Long id);

}
