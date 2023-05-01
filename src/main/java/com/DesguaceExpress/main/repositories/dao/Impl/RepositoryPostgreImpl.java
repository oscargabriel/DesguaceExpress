package com.DesguaceExpress.main.repositories.dao.Impl;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.*;
import com.DesguaceExpress.main.exception.custom.DataNotFound;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * implementacion del repositorio, se usa una base de datos de PostgreSQL, se hacen las consultas con EntityManager
 * y se hacen mediante HQL como dialecto para consultar
 */
@Repository
public class RepositoryPostgreImpl implements RepositoryDesguace {

    @PersistenceContext//definir la variable para las consultas
    private EntityManager entityManager;

    @Override
    public List<Top10VehicleInParking> TopVehicleInParking() {
        List<Top10VehicleInParking> top10Vehicle = new ArrayList<Top10VehicleInParking>();
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, count(v.id), v.licensePlate, v.type, v.make, v.model, CONCAT(m.firstName, ' ',m.lastName), m.document " +
                        "FROM VehicleParking vp " +
                        "JOIN Vehicle v ON v.id=vp.vehicleId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "GROUP BY v.id, v.licensePlate, v.type, v.make, v.model, CONCAT(m.firstName, ' ',m.lastName), m.document " +
                        "ORDER BY count(v.id) desc " +
                        "limit 10 ", Object[].class
        );

        try {
            query.getResultList().forEach(x->
                    top10Vehicle.add(Top10VehicleInParking.builder()
                            .id((Long) x[0])
                            .count((Long) x[1])
                            .licensePlate((String) x[2])
                            .type((String) x[3])
                            .make((String) x[4])
                            .model((String) x[5])
                            .owner((String) x[6])
                            .document((String) x[7])
                            .build()
            ));
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"resultados para la busqueda");
        }
        if(top10Vehicle.size()==0){//verifica que encontrara resultados
            throw new DataNotFound(HttpStatus.NOT_FOUND,"resultados para la busqueda");
        }
        return top10Vehicle;
    }

    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        TypedQuery<Vehicle> query = entityManager.createQuery(
                "FROM Vehicle v " +
                        "WHERE v.licensePlate=:licensePlate",Vehicle.class);
        query.setParameter("licensePlate",licensePlate);
        Vehicle vehicle=null;
        try {
            vehicle = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"la placa "+licensePlate);
        }
        return vehicle;
    }

    @Override
    public Parking findParkingById(Long id) {
        TypedQuery<Parking> query = entityManager.createQuery(
                "FROM Parking p " +
                        "WHERE p.id=:id",Parking.class);
        query.setParameter("id",id);
        Parking parking=null;
        try {
            parking = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueadero con id "+id);
        }
        return parking;
    }

    @Override
    public Parking findParkingByName(String parkingName) {
        Parking parking;
        TypedQuery<Parking> query = entityManager.createQuery(
                "FROM Parking p " +
                        "WHERE p.name=:parking",Parking.class);
        query.setParameter("parking",parkingName);
        try {
            parking = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueadero con nombre "+parkingName);
        }
        if(parking==null){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueadero con nombre "+parkingName);
        }
        return parking;
    }

    @Override
    public VehicleParking findRegisterOpenByLicencePlate(String licensePlate){
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT vp.id, vp.entry, vp.exit, vp.vehicleId, vp.parkingId " +
                        "FROM VehicleParking vp " +
                        "JOIN Vehicle v ON v.id = vp.vehicleId " +
                        "WHERE vp.exit IS NULL AND v.licensePlate =:licensePlate ",Object[].class
        );
        query.setParameter("licensePlate", licensePlate);
        //si no encuentra valores genera un NoResultException, se atrapa y se retorna null y se trata
        //donde lo llamaron
        Object[] objects=null;
        try {
            objects = query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        VehicleParking vehicleParking = VehicleParking.builder()
                .id((Long) objects[0])
                .entry( ((LocalDateTime) objects[1]))
                .exit( ((LocalDateTime) objects[2]))
                .vehicleId((Vehicle) objects[3])
                .parkingId((Parking) objects[4])
                .build();
        return vehicleParking;
    }

    @Override
    public List<VehicleByParking> findVehicleByParkingId(Long id) {
        List<VehicleByParking> vehicleByParkings = new ArrayList<>();
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, v.type, v.make, v.model, v.year, CONCAT(m.firstName, ' ',m.lastName), m.document, vp.entry " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id = vp.vehicleId " +
                        "JOIN Parking p ON p.id=vp.parkingId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "WHERE p.id =:id AND vp.exit IS NULL ",Object[].class
        );
        query.setParameter("id",id);
        try {
            query.getResultList().forEach(x ->
                    vehicleByParkings.add(VehicleByParking.builder()
                            .id((Long) x[0])
                            .licensePlate((String) x[1])
                            .type((String) x[2])
                            .make((String) x[3])
                            .model((String) x[4])
                            .year((Integer) x[5])
                            .owner((String) x[6])
                            .document((String) x[7])
                            .entry((LocalDateTime) x[8])
                            .build())
            );
        } catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"no se encontraron vehiculos en el parqueadero "+id);
        }
        if(vehicleByParkings.size()==0){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"no se encontraron vehiculos en el parqueadero "+id);
        }
        return vehicleByParkings;
    }

    @Override
    public Members findMemberByDocument(String memberDocument) {
        TypedQuery<Members> query = entityManager.createQuery(
                "FROM Members m " +
                        "WHERE m.document=:memberDocument ",Members.class);
        query.setParameter("memberDocument",memberDocument);
        Members member = null;
        try {
            member = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"documento "+memberDocument);
        }
        return member;
    }

    @Override
    public Members findMemberById(Long id) {
        TypedQuery<Members> query = entityManager.createQuery(
                "FROM Members m " +
                        "WHERE m.id=:id ",Members.class);
        query.setParameter("id",id);
        Members member = null;
        try {
            member = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"socio con id "+id);
        }
        return member;
    }

    @Override
    public List<VehicleInParkingByMembers> VehicleInParkingByMembers(Long id, String name) {
        List<VehicleInParkingByMembers> vehicle = new ArrayList<>();
        HashMap<String ,String> infoParking = new HashMap<>();

        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate , vp.entry, p.name , CONCAT(l.country,', ',l.departament,', ', l.state,', ',l.ubication)" +
                        "FROM Members m " +
                        "JOIN Vehicle v ON m.id=v.membersId " +
                        "JOIN VehicleParking vp ON vp.vehicleId=v.id " +
                        "JOIN Parking p ON p.id=vp.parkingId " +
                        "JOIN Location l ON p.locationId = l.id " +
                        "WHERE vp.exit IS NULL AND m.id=:id ",Object[].class);
        query.setParameter("id",id);
        try {
            query.getResultList().forEach(x->{
                        infoParking.put("parkingName",(String) x[3]);
                        infoParking.put("Location",(String) x[4]);
                        vehicle.add(VehicleInParkingByMembers.builder()
                                        .id((Long) x[0])
                                        .licensePlate((String) x[1])
                                        .entryParking((LocalDateTime) x[2])
                                        .parking(infoParking)
                                .build()
                        );
            });
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND," vehiculo en los parqueaderos");
        }
        if(vehicle.size()==0){
            throw new DataNotFound(HttpStatus.NOT_FOUND," vehiculo en los parqueaderos");
        }
        return vehicle;
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        Vehicle vehicle = null;
        TypedQuery<Vehicle> query = entityManager.createQuery(
                "From Vehicle v " +
                        "WHERE v.id =: id",Vehicle.class
        );
        query.setParameter("id",id);
        try {
            vehicle = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculo con id "+ id);
        }
        return vehicle;
    }

    @Override
    public VehicleDetails findVehicleDetailsById(Long id) {
        Long pid = null;
        VehicleDetails vehicleDetails;
        HashMap<String ,String> infoParking = new HashMap<>();
        Boolean previus = false;
        //busca si esta en en un parking, si no esta termina la ejecucion y retorna un throw
        //si encuentra solo extrae el id del parqueadero en el que esta
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT p.id " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id=vp.vehicleId " +
                        "JOIN Parking p ON vp.parkingId=p.id " +
                        "WHERE v.id=:id AND vp.exit IS NULL ", Long.class
        );
        query.setParameter("id",id);
        try {
            pid = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculo en un parqueadero");
        }
        //si lo encuentra busca todos los datos para llenar la clase que se va a mostrar
        TypedQuery<Object[]> query2 = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, vp.entry, p.name, CONCAT(l.country,', ',l.departament,', ', l.state,', ',l.ubication) " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id=vp.vehicleId " +
                        "JOIN Parking p ON vp.parkingId=p.id " +
                        "JOIN Location l ON p.locationId = l.id " +
                        "WHERE v.id=:id AND p.id =:pid ",Object[].class
        );
        query2.setParameter("id",id);
        query2.setParameter("pid",pid);
        List<Object[]> objects = query2.getResultList();

        infoParking.put("name",(String) objects.get(0)[3]);
        infoParking.put("location",(String) objects.get(0)[4]);
        //si aparece mas de una vez guarda un true
        previus = objects.size() > 1;

        vehicleDetails = VehicleDetails.builder()
                .id((Long) objects.get(0)[0])
                .licensePlate((String) objects.get(0)[1])
                .entry((LocalDateTime) objects.get(0)[2])
                .parking(infoParking)
                .previous(previus)
                .build();

        return vehicleDetails;
    }

    @Override
    public EmailBodySend VehicleInParkingByLicensePlate(EmailBodyPre emailBodyPre) {
        Object[] objects=null;
        EmailBodySend emailBodySend;
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.licensePlate, m.email, p.name, p.id " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id=vp.vehicleId " +
                        "JOIN Parking p ON vp.parkingId=p.id " +
                        "JOIN Members m ON m.id = v.membersId " +
                        "WHERE v.licensePlate=:licensePlate AND vp.exit IS NULL ", Object[].class
        );
        query.setParameter("licensePlate",emailBodyPre.getPlaca());

        try {//busca si esta el vehiculo en un parking
            objects = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculo en un parqueadero");
        }
        //verifica que coincida con el id del parking
        if(emailBodyPre.getParqueaderoId()!=(Long) objects[3]){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"el vehiculo dentro del parqueadero");
        }
        //verifica que el email coincida con el dueño del vehiculo
        if(!emailBodyPre.getEmail().equals((String) objects[1])){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"coincidencia con el email");
        }

        emailBodySend = EmailBodySend.builder()
                .placa((String) objects[0])
                .email((String) objects[1])
                .parqueaderoNombre((String) objects[2])
                .mensaje(emailBodyPre.getMensaje())
                .build();

        return emailBodySend;
    }

    @Override
    public List<Top10VehicleInParking> TopVehicleInParkingByParkingId(Long id) {
        List<Top10VehicleInParking> top10Vehicle = new ArrayList<Top10VehicleInParking>();
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, count(v.id), v.licensePlate, v.type, v.make, v.model, CONCAT(m.firstName, ' ',m.lastName), m.document " +
                        "FROM VehicleParking vp " +
                        "JOIN Vehicle v ON v.id=vp.vehicleId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "JOIN Parking p ON vp.parkingId=p.id " +
                        "WHERE p.id=:id " +
                        "GROUP BY v.id, v.licensePlate, v.type, v.make, v.model, CONCAT(m.firstName, ' ',m.lastName), m.document " +
                        "ORDER BY count(v.id) desc, v.licensePlate " +
                        "limit 10 ", Object[].class
        );
        query.setParameter("id",id);
        try {
            query.getResultList().forEach(x->
                    top10Vehicle.add(Top10VehicleInParking.builder()
                            .id((Long) x[0])
                            .count((Long) x[1])
                            .licensePlate((String) x[2])
                            .type((String) x[3])
                            .make((String) x[4])
                            .model((String) x[5])
                            .owner((String) x[6])
                            .document((String) x[7])
                            .build()
                    ));
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"resultados para la busqueda");
        }
        if(top10Vehicle.size()==0){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"resultados para la busqueda");
        }
        return top10Vehicle;
    }

    @Override
    public List<VehicleDetails> VehicleInParkingForTheFirstTime() {
        List<VehicleDetails> vehicleDetails = new ArrayList<>();
        //busca los vehiculos que esten en un parqueadero
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, vp.entry, p.name, CONCAT(l.country,', ',l.departament,', ', l.state,', ',l.ubication) " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id=vp.vehicleId " +
                        "JOIN Parking p ON vp.parkingId=p.id " +
                        "JOIN Location l ON l.id=p.locationId " +
                        "WHERE vp.exit IS NULL ", Object[].class
        );

        List<Object[]> objects = query.getResultList();

        if(objects.size()==0){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculos en los estacionamientos");
        }
        objects.forEach(x->{
            HashMap<String ,String> infoParking = new HashMap<>();

            infoParking.put("name",(String) x[3]);
            infoParking.put("location",(String) x[4]);
            VehicleDetails vehicle = VehicleDetails.builder()
                    .id((Long) x[0])
                    .licensePlate((String) x[1])
                    .entry((LocalDateTime) x[2])
                    .parking(infoParking)
                    .build();
            vehicleDetails.add(vehicle);
        });
        //busca cuantas veces aparece el vehiculo en algun parqueadero
        for(int i=0;i<vehicleDetails.size();i++){
            TypedQuery<Long> query2 = entityManager.createQuery(
                    "SELECT COUNT(v.id) " +
                            "FROM VehicleParking vp " +
                            "JOIN Vehicle v ON v.id=vp.vehicleId " +
                            "WHERE v.id=:id " +
                            "GROUP BY v.id ",Long.class);
            query2.setParameter("id",vehicleDetails.get(i).getId());
            Long count = query2.getSingleResult();
            vehicleDetails.get(i).setPrevious((count>1));

        }

        return vehicleDetails;

    }

    @Override
    public Double FindEarningsByDate(Long parkingId, LocalDateTime dateInit, LocalDateTime dateEnd) {
        Double cost=0D;
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT SUM(vp.cost) " +
                        "FROM Parking p " +
                        "JOIN VehicleParking vp ON p.id=vp.parkingId " +
                        "WHERE p.id=:parkingId AND vp.exit BETWEEN :dateInit AND :dateEnd ",Double.class);
        query.setParameter("parkingId",parkingId);
        query.setParameter("dateInit",dateInit);
        query.setParameter("dateEnd",dateEnd);
        cost=query.getSingleResult();
        return (cost!=null)?cost:0D;
    }

    @Override
    public MaximumIncome MaximumIncomeForDay(Long id) {
        MaximumIncome maximumIncome;
        TypedQuery<Object[]> query = entityManager.createQuery(
                "select sum(vp.cost), to_char(vp.exit,'dd-mm-yyyy') " +
                        "from Parking p " +
                        "join VehicleParking vp on p.id=vp.parkingId " +
                        "where p.id=:id and vp.exit is not null " +
                        "group by to_char(vp.exit,'dd-mm-yyyy') " +
                        "order by sum(vp.cost) desc " +
                        "limit 1 ",Object[].class
        );
        query.setParameter("id",id);
        Object[] objects;
        try {//busca si encuentra ganancias registradas en el parqueadero o evade el fisco
            objects = query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"ganancias registradas en el parqueadero");
        }
        maximumIncome = MaximumIncome.builder()
                .income((Double) objects[0])
                .day((String) objects[1])
                .build();
        return maximumIncome;
    }

    @Override
    public List<Top3Parking> Top3ParkingThisYear(LocalDateTime dateInit, LocalDateTime dateEnd) {
        List<Top3Parking> top3Parkings = new ArrayList<>();
        TypedQuery<Object[]> query = entityManager.createQuery(
                "select sum(vp.cost), p.name " +
                        "from Parking p " +
                        "join VehicleParking vp on p.id=vp.parkingId " +
                        "where vp.exit is not null and vp.exit between :dateInit and :dateEnd " +
                        "group by p.name " +
                        "order by sum(vp.cost) desc " +
                        "limit 3 ",Object[].class);
        query.setParameter("dateInit",dateInit);
        query.setParameter("dateEnd",dateEnd);

        query.getResultList().forEach(x->
                top3Parkings.add(Top3Parking.builder()
                                .income((Double) x[0])
                                .parkingName((String) x[1])
                        .build()));
        if(top3Parkings.size()==0){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueaderos para la busqueda");
        }
        return top3Parkings;
    }

//-----------------------------------------------------------------------------------------------
    @Override
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, v.type, v.make, v.model, v.year, CONCAT(m.firstName, ' ',m.lastName), m.document, vp.entry " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id = vp.vehicleId " +
                        "JOIN Parking p ON p.id=vp.parkingId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "WHERE p.id =:id " +
                        "ORDER BY vp.entry DESC ",Object[].class
        );
        query.setParameter("id",id);

        return QueryDataPartial(query,id);
    }

    @Override
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id, String licensePlate) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, v.type, v.make, v.model, v.year, CONCAT(m.firstName, ' ',m.lastName), m.document, vp.entry " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id = vp.vehicleId " +
                        "JOIN Parking p ON p.id=vp.parkingId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "WHERE p.id =:id AND position(:licensePlate in v.licensePlate)>0 " +
                        "ORDER BY vp.entry DESC ",Object[].class
        );
        query.setParameter("id",id);
        query.setParameter("licensePlate",licensePlate);
        return QueryDataPartial(query,id);
    }

    @Override
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id, LocalDateTime dateInit, LocalDateTime dateEnd) {
        List<VehicleByParking> vehicleByParkings = new ArrayList<>();
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, v.type, v.make, v.model, v.year, CONCAT(m.firstName, ' ',m.lastName), m.document, vp.entry " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id = vp.vehicleId " +
                        "JOIN Parking p ON p.id=vp.parkingId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "WHERE p.id =:id AND vp.entry BETWEEN :dateInit AND :dateEnd " +
                        "ORDER BY vp.entry DESC ",Object[].class
        );
        query.setParameter("id",id);
        query.setParameter("dateInit",dateInit);
        query.setParameter("dateEnd",dateEnd);
        return QueryDataPartial(query,id);
    }

    @Override
    public List<VehicleByParking> findVehicleByParkingIdAndDataPartial(Long id, String licensePlate, LocalDateTime dateInit, LocalDateTime dateEnd) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT v.id, v.licensePlate, v.type, v.make, v.model, v.year, CONCAT(m.firstName, ' ',m.lastName), m.document, vp.entry " +
                        "FROM Vehicle v " +
                        "JOIN VehicleParking vp ON v.id = vp.vehicleId " +
                        "JOIN Parking p ON p.id=vp.parkingId " +
                        "JOIN Members m ON m.id=v.membersId " +
                        "WHERE p.id =:id AND position(:licensePlate in v.licensePlate)>0 AND vp.entry BETWEEN :dateInit AND :dateEnd " +
                        "ORDER BY vp.entry DESC ",Object[].class
        );
        query.setParameter("id",id);
        query.setParameter("licensePlate",licensePlate);
        query.setParameter("dateInit",dateInit);
        query.setParameter("dateEnd",dateEnd);
        return QueryDataPartial(query,id);
    }

    @Override
    public List<VehicleByParking> QueryDataPartial(TypedQuery<Object[]> query, Long id) {
        List<VehicleByParking> vehicleByParkings = new ArrayList<>();
        try {
            query.getResultList().forEach(x ->
                    vehicleByParkings.add(VehicleByParking.builder()
                            .id((Long) x[0])
                            .licensePlate((String) x[1])
                            .type((String) x[2])
                            .make((String) x[3])
                            .model((String) x[4])
                            .year((Integer) x[5])
                            .owner((String) x[6])
                            .document((String) x[7])
                            .entry((LocalDateTime) x[8])
                            .build())
            );
        } catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculos en el parqueadero "+id);
        }
        if(vehicleByParkings.size()==0){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculos en el parqueadero "+id);
        }
        return vehicleByParkings;
    }

    @Override
    public Long FindMemberInParking(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT p.id " +
                        "From Parking p " +
                        "WHERE p.id=:id and p.membersId is not null ",Long.class
        );
        query.setParameter("id",id);
        try {//si esta ocupado retorna true, caso contrario retorna false
            return query.getSingleResult();
        }catch (NoResultException e){
            return 0L;
        }
    }

    @Override
    public Long FindMemberInParkingsByMember(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT p.id " +
                        "From Parking p " +
                        "JOIN Members m ON p.membersId=m.id " +
                        "WHERE m.Id=:id and p.membersId is not null ",Long.class
        );
        query.setParameter("id",id);
        try {//si esta ocupado retorna true, caso contrario retorna false
            return query.getSingleResult();
        }catch (NoResultException e){
            return 0L;
        }
    }

    @Override
    public List<Long> FindVehicleIdByMemberId(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT v.id " +
                        "From Members m " +
                        "JOIN Vehicle v ON m.id=v.membersId " +
                        "WHERE m.id=:id ",Long.class
        );
        query.setParameter("id",id);
        try {
            return query.getResultList();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueadero id "+id);
        }
    }

    @Override
    public List<Long> FindVehicleParkingIdByMemberId(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT vp.id " +
                        "From VehicleParking vp " +
                        "JOIN Vehicle v ON v.id=vp.vehicleId " +
                        "WHERE v.id=:id ",Long.class
        );
        query.setParameter("id",id);
        try {
            return query.getResultList();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueadero id "+id);
        }
    }

    @Override
    public Vehicle FindVehicleById(Long id) {
        TypedQuery<Vehicle> query = entityManager.createQuery(
                        "From Vehicle v " +
                        "WHERE v.id=:id ",Vehicle.class
        );
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"vehiculo id "+id);
        }
    }

    @Override
    public Location FindLocationById(Long id) {
        TypedQuery<Location> query = entityManager.createQuery(
                "From Location l " +
                        "WHERE l.id=:id ",Location.class
        );
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"la locationId id "+id);
        }
    }

    @Override
    public Parking FindParkingById(Long id) {
        TypedQuery<Parking> query = entityManager.createQuery(
                "From Parking p " +
                        "WHERE p.id=:id ",Parking.class
        );
        query.setParameter("id",id);
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"parqueadero id "+id);
        }
    }

    @Override
    public Boolean FindIfLicensePlateIsInUse(String licensePlate, Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT v.id " +
                        "From Vehicle v " +
                        "WHERE v.licensePlate=:licensePlate ",Long.class
        );
        query.setParameter("licensePlate",licensePlate);
        Long l;
        try {
            l = query.getSingleResult();
        }catch (NoResultException e){
            return false;
        }
        return !Objects.equals(l, id);
    }

    @Override
    public Boolean FindIfDocumentIsInUse(String document, Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT m.id " +
                        "From Members m " +
                        "WHERE m.document=:document ",Long.class
        );
        query.setParameter("document",document);
        Long l;
        try {
            l = query.getSingleResult();
        }catch (NoResultException e){
            return false;
        }
        return !Objects.equals(l, id);
    }

    @Override
    public Boolean FindIfEmailIsInUse(String email, Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT m.id " +
                        "From Members m " +
                        "WHERE m.email=:email ",Long.class
        );
        query.setParameter("email",email);
        Long l;
        try {
            l = query.getSingleResult();
        }catch (NoResultException e){
            return false;
        }
        return !Objects.equals(l, id);
    }

    @Override
    public Boolean FindIfPhoneIsInUse(Long phone, Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT m.id " +
                        "From Members m " +
                        "WHERE m.phone=:phone ",Long.class
        );
        query.setParameter("phone",phone);
        Long l;
        try {
            l = query.getSingleResult();
        }catch (NoResultException e){
            return false;
        }
        return !Objects.equals(l, id);
    }

    @Override
    public Boolean FindIfParkingNameIsInUse(String parkingName, Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT p.id " +
                        "From Parking p " +
                        "WHERE p.name=:name ",Long.class
        );
        query.setParameter("name",parkingName);
        Long l;
        try {
            l = query.getSingleResult();
        }catch (NoResultException e){
            return false;
        }
        return !Objects.equals(l, id);
    }


}
