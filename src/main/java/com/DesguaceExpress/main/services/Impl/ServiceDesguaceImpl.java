package com.DesguaceExpress.main.services.Impl;

import com.DesguaceExpress.main.dto.Top10VehicleInParking;
import com.DesguaceExpress.main.dto.VehicleByParking;
import com.DesguaceExpress.main.dto.VehicleDetails;
import com.DesguaceExpress.main.dto.VehicleInParkingByMembers;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.VehicleParking;
import com.DesguaceExpress.main.exception.custom.NoMemberInTheParking;
import com.DesguaceExpress.main.exception.custom.VehicleRegistryIsBad;
import com.DesguaceExpress.main.repositories.dao.Impl.RepositoryPostgreImpl;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import com.DesguaceExpress.main.repositories.jpa.MembersRepository;
import com.DesguaceExpress.main.repositories.jpa.ParkingRepository;
import com.DesguaceExpress.main.repositories.jpa.VehicleParkingRepository;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

@Service
public class ServiceDesguaceImpl implements ServiceDesguace {

    RepositoryDesguace repositoryDesguace;

    @Autowired
    MembersRepository membersRepository;

    @Autowired
    VehicleParkingRepository vehicleParkingRepository;

    @Autowired
    ParkingRepository parkingRepository;


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
        //si no hay socio vinculado al parqueadero genera una excepcion y no registra la entrada
        if(vehicleParking.getParkingId().getMembersId()==null){
            throw new NoMemberInTheParking(HttpStatus.NOT_ACCEPTABLE, vehicleParking.getParkingId().getName());
        }
        //busca la placa, si encuentra una que un tiket abierto genera una excepcion
        if(repositoryDesguace.findRegisterOpenByLicencePlate(licensePlate)!=null) {
            throw new VehicleRegistryIsBad(
                    HttpStatus.BAD_REQUEST,
                    "No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero"
            );
        }
        //verifica que no se alcance la capacidad maxima en el parqueadero
        if(vehicleParking.getParkingId().getMaxCapacity()<=vehicleParking.getParkingId().getCurrentCapacity()){
            throw new VehicleRegistryIsBad(
                    HttpStatus.BAD_REQUEST,
                    "No se puede Registrar Ingreso, el parqueadero esta lleno"
            );
        }

        HashMap<String,Long> hashMap = new HashMap<>();
        vehicleParking.getParkingId().setCurrentCapacity(vehicleParking.getParkingId().getCurrentCapacity()+1);
        parkingRepository.save(vehicleParking.getParkingId());
        hashMap.put("id",vehicleParkingRepository.save(vehicleParking).getId());
        return hashMap;
    }

    @Override
    public HashMap<String, String> RegistrarSalida(String licensePlate, Long idParking) {

        VehicleParking vehicleParking = repositoryDesguace.findRegisterOpenByLicencePlate(licensePlate);

        if(vehicleParking==null){
            throw new VehicleRegistryIsBad(
                    HttpStatus.BAD_REQUEST,
                    "No se puede Registrar Salida, no existe la placa en el parqueadero"
            );
        }

        if(vehicleParking.getParkingId().getId()!=idParking){
            throw new VehicleRegistryIsBad(
                    HttpStatus.BAD_REQUEST,
                    "No estan permitidos los agujeros de gusano entre parqueaderos, salga por el parqueadero id: "+vehicleParking.getParkingId().getId()
            );
        }
        vehicleParking.getParkingId().setCurrentCapacity(vehicleParking.getParkingId().getCurrentCapacity()-1);
        vehicleParking.setExit(LocalDateTime.now());
        vehicleParking.setCost(
                ChronoUnit.HOURS.between(vehicleParking.getEntry(), vehicleParking.getExit())
                        *vehicleParking.getParkingId().getCostHour()
        );
        System.out.println(vehicleParking.getEntry()+" entrada");
        System.out.println(vehicleParking.getExit()+" salida");
        System.out.println(vehicleParking.getParkingId().getCostHour()+" costo");
        System.out.println(ChronoUnit.HOURS.between(vehicleParking.getEntry(), vehicleParking.getExit()));
        System.out.println(vehicleParking.getCost()+" hola mundo");
        parkingRepository.save(vehicleParking.getParkingId());
        vehicleParkingRepository.save(vehicleParking);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id","salida registrada");
        return hashMap;
    }

    @Override
    public List<VehicleByParking> findVehiclesByParking(String parkingName) {
        Parking parking = repositoryDesguace.findParkingByName(parkingName);
        return repositoryDesguace.findVehicleByParkingId(parking.getId(), parking.getName());
    }

    @Override
    public List<VehicleInParkingByMembers> findVehiclesByMember(String memberDocument) {
        //1 buscar socio por documento si no se encuentra devoler un throw
        Members members = repositoryDesguace.findMemberByDocument(memberDocument);
        //2 buscar vehiculos del socio que esten parqueados si no encuentra devolver un 200

        return repositoryDesguace.VehicleInParkingByMembers(members.getId(),members.getFirstName()+" "+members.getLastName());
    }

    @Override
    public VehicleDetails findVehicleDetailsById(Long id) {
        repositoryDesguace.findVehicleById(id);
        return repositoryDesguace.findVehicleDetailsById(id);
    }

    @Override
    public String crearSocio(Members members) {
        members.setId(repositoryDesguace.MembersID());
        membersRepository.save(members);
        return "creacion exitosa";
    }
}
