package com.DesguaceExpress.main.repositories.dao.Impl;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.dto.VehicleByParking;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.entities.VehicleParking;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RepositoryPostgreImplTest {

    @Autowired
    RepositoryPostgreImpl repositoryPostgre;


    @Test
    void topVehicleInParking() {
        int size = repositoryPostgre.TopVehicleInParking().size();
        assertEquals(10,size);
    }

    @Test
    void findVehicleByLicensePlate() {
        Vehicle vehicle = repositoryPostgre.findVehicleByLicensePlate("9F0440");
        assertEquals(1,vehicle.getId());
    }

    @Test
    void findParkingById() {
        Parking parking = repositoryPostgre.findParkingById(10L);
        assertEquals("Kling-Streich",parking.getName());
    }

    @Test
    void findParkingByName() {
        Parking parking = repositoryPostgre.findParkingByName("Kling-Streich");
        assertEquals(10L,parking.getId());
    }

    @Test
    void findRegisterOpenByLicencePlate() {
        VehicleParking vehicleParking = repositoryPostgre.findRegisterOpenByLicencePlate("D5359D");
        assertEquals(108L,vehicleParking.getId());
    }

    @Test
    void findRegisterCloseByLicencePlate() {
        VehicleParking vehicleParking = repositoryPostgre.findRegisterOpenByLicencePlate("9F0440");
        assertNull(vehicleParking);
    }


    @Test
    void findVehicleByParkingId() {
        List<VehicleByParking> vehicle = repositoryPostgre.findVehicleByParkingId(10L);
    }
}