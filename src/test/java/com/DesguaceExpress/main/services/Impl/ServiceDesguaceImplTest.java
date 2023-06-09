package com.DesguaceExpress.main.services.Impl;

import com.DesguaceExpress.main.dto.PartialData;
import com.DesguaceExpress.main.dto.VehicleByParking;
import com.DesguaceExpress.main.exception.custom.NoMemberInTheParking;
import com.DesguaceExpress.main.exception.custom.VehicleRegistryIsBad;
import org.hibernate.envers.Audited;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceDesguaceImplTest {

    @Autowired
    ServiceDesguaceImpl serviceDesguace;

    @Test
    void topVehicleInParking() {
        int size = serviceDesguace.TopVehicleInParking().size();
        assertEquals(10, size);
    }

    @Test
    void registrarEntradaExistiendoUna() {
        assertThrows(VehicleRegistryIsBad.class,()->{
            serviceDesguace.RegistrarEntrada("D5359D", 1L);
        });

    }

    @Test
    void registrarEntradaCorrecta() {
        HashMap<String, Long> hashMap = serviceDesguace.RegistrarEntrada("B67C4A", 1L);
        assertTrue(hashMap.get("id")>0);

    }

    @Test
    void registrarSalidaCorrecta() {
        HashMap<String, String> hashMap = serviceDesguace.RegistrarSalida("D5359D",10L);
        assertEquals("salida registrada",hashMap.get("id"));
    }

    @Test
    void registrarSalidaSinExistir() {
        assertThrows(VehicleRegistryIsBad.class,()->{
            serviceDesguace.RegistrarSalida("55CD72",4L);
        });

    }

    @Test
    void registrarSalidaSinParqueaderoIncorrecto() {
        assertThrows(VehicleRegistryIsBad.class,()->{
            serviceDesguace.RegistrarSalida("44CC79",10L);
        });
    }

    @Test
    void vehiclesInAParkingByPartialData() {
        PartialData partialData = PartialData.builder()
                .parkingId(10L)
                .dateEnd("01-01-2023")
                .dateInit("01-01-2000")
                .partialLicensePlate("5")
                .build();
        List<VehicleByParking> vehicleByParkings = serviceDesguace.VehiclesInAParkingByPartialData(partialData);
        assertNotEquals(null,vehicleByParkings);
    }

    @Test
    void deleteMember() {
        System.out.println(serviceDesguace.DeleteMember(2L));
    }
}