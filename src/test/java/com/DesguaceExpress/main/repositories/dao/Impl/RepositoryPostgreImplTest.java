package com.DesguaceExpress.main.repositories.dao.Impl;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.entities.VehicleParking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RepositoryPostgreImplTest {

    @Autowired
    RepositoryPostgreImpl repositoryPostgre;
/*

    @Test
    void topVehicleInParking() {
        int size = repositoryPostgre.TopVehicleInParking().size();
        System.out.println(size);
        assertEquals(10,size);
    }

    @Test
    void findVehicleByLicensePlate() {
        Vehicle vehicle = repositoryPostgre.findVehicleByLicensePlate("9F-04-40-45-18-D5");
        System.out.println(vehicle.getId());
        assertEquals(1,vehicle.getId());
    }

    @Test
    void findParkingById() {
        Parking parking = repositoryPostgre.findParkingById(10L);
        System.out.println(parking.getName());
        assertEquals("Kling-Streich",parking.getName());
    }*/

    @Test
    void findRegisterOpenByLicencePlate() {
        VehicleParking vehicleParking = repositoryPostgre.findRegisterOpenByLicencePlate("D5-35-9D-3F-E4-9F");
        System.out.println(vehicleParking.getEntry());
        assertEquals(108L,vehicleParking.getId());
    }
}