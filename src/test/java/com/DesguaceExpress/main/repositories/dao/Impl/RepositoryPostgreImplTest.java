package com.DesguaceExpress.main.repositories.dao.Impl;

import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Members;
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

/*
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
        List<VehicleByParking> vehicle = repositoryPostgre.findVehicleByParkingId(10L," hola mundo");
        assertNotEquals(null,vehicle);
    }


    @Test
    void findMemberByDocument() {
        Members members = repositoryPostgre.findMemberByDocument("6474791055");
        assertEquals(2,members.getId());
    }

    @Test
    void vehicleInParkingByMembers() {
        List<VehicleInParkingByMembers> vehicle = repositoryPostgre.VehicleInParkingByMembers(10L,"hola mundo");
        assertTrue(vehicle.size()>0);
    }

    @Test
    void findVehicleById() {
        Vehicle vehicle = repositoryPostgre.findVehicleById(10L);
        assertEquals("2A0CC8", vehicle.getLicensePlate());
    }*/

    @Test
    void findVehicleDetailsById() {
        VehicleDetails vehicleDetails = repositoryPostgre.findVehicleDetailsById(40L);
        System.out.println(vehicleDetails.toString());
    }

    @Test
    void vehicleInParkingByLicensePlate() {
        EmailBodyPre emailBodyPre = EmailBodyPre.builder()
                .email("wcameli3@yahoo.co.jp")
                .placa("55CD72")
                .parqueaderoId(10L)
                .mensaje("hola mundo")
                .build();
        EmailBodySend emailBodySend = repositoryPostgre.VehicleInParkingByLicensePlate(emailBodyPre);
        System.out.println(emailBodySend);
    }
}