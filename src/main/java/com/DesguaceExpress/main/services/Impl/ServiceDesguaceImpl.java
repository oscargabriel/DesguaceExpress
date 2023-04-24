package com.DesguaceExpress.main.services.Impl;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.VehicleParking;
import com.DesguaceExpress.main.repositories.dao.Impl.RepositoryPostgreImpl;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import com.DesguaceExpress.main.repositories.jpa.MembersRepository;
import com.DesguaceExpress.main.repositories.jpa.VehicleParkingRepository;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class ServiceDesguaceImpl implements ServiceDesguace {

    RepositoryDesguace repositoryDesguace;

    @Autowired
    MembersRepository membersRepository;

    @Autowired
    VehicleParkingRepository vehicleParkingRepository;

    public ServiceDesguaceImpl(RepositoryPostgreImpl repositoryDesguace) {
        this.repositoryDesguace = repositoryDesguace;
    }

    @Override
    public List<Top10VehicleInParking> TopVehicleInParking() {
        return repositoryDesguace.TopVehicleInParking();
    }


    @Override
    public HashMap<String, Long> RegistrarEntrada(String licensePlate, Long idParking) {
        VehicleParking vehicleParking = VehicleParking.builder()
                .id(repositoryDesguace.VehicleParkingID())
                .parkingId(repositoryDesguace.findParkingById(idParking))
                .vehicleId(repositoryDesguace.findVehicleByLicensePlate(licensePlate))
                .build();
        HashMap<String,Long> hashMap = new HashMap<>();
        hashMap.put("id",vehicleParkingRepository.save(vehicleParking).getId());

        return hashMap;
    }




    @Override
    public String crearSocio(Members members) {
        members.setId(repositoryDesguace.MembersID());
        membersRepository.save(members);
        return "creacion exitosa";
    }
}
