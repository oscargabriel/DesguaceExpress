package com.DesguaceExpress.main.repositories.dao.Impl;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    //regresar los 10 vehículos que más veces se han registrado en los diferentes parqueaderos y
    // cuantas veces han sido
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
                "FROM Vehicle v" +
                        "WHERE v.licensePlate=:licensePlate",Vehicle.class);
        query.setParameter(licensePlate,licensePlate);
        return query.getSingleResult();
    }
}
