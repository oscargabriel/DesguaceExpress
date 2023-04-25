package com.DesguaceExpress.main.repositories.dao.Impl;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.dto.VehicleByParking;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.entities.VehicleParking;
import com.DesguaceExpress.main.exception.custom.DataNotFound;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import jakarta.persistence.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * implementacion del repositorio, se usa una base de datos de PostgreSQL, se hacen las consultas con EntityManager
 * y se hacen mediante HQL como dialecto para consultar
 */
@Repository
public class RepositoryPostgreImpl implements RepositoryDesguace {

    @PersistenceContext//definir la variable para las consultas
    private EntityManager entityManager;


    @Override
    public Long LocationID() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(id) FROM Location",Long.class);
        return query.getSingleResult()+1L;
    }

    @Override
    public Long MembersID() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(id) FROM Members",Long.class);
        return query.getSingleResult()+1L;
    }

    @Override
    public Long ParkingID() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(id) FROM Parking",Long.class);
        return query.getSingleResult()+1L;
    }

    @Override
    public Long VehicleID() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(id) FROM Vehicle",Long.class);
        return query.getSingleResult()+1L;
    }

    @Override
    public Long VehicleParkingID() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(id) FROM VehicleParking",Long.class);
        return query.getSingleResult()+1L;
    }

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
        TypedQuery<Parking> query = entityManager.createQuery(
                "FROM Parking p " +
                        "WHERE p.name=:parking",Parking.class);
        query.setParameter("parking",parkingName);
        Parking parking = query.getSingleResult();
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
        query.getResultList().forEach(x->
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
        return vehicleByParkings;
    }

    @Override
    public Member findMemberByName(String memberName) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(id) FROM Members",Long.class);
        return null;
    }
}
