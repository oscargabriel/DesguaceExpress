package com.DesguaceExpress.main.services.Impl;

import com.DesguaceExpress.main.controllers.Impl.ControllerDesguaceImpl;
import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.*;
import com.DesguaceExpress.main.exception.custom.DataIsInUse;
import com.DesguaceExpress.main.exception.custom.DataNotFound;
import com.DesguaceExpress.main.exception.custom.NoMemberInTheParking;
import com.DesguaceExpress.main.exception.custom.VehicleRegistryIsBad;
import com.DesguaceExpress.main.repositories.dao.Impl.RepositoryPostgreImpl;
import com.DesguaceExpress.main.repositories.dao.RepositoryDesguace;
import com.DesguaceExpress.main.repositories.jpa.*;
import com.DesguaceExpress.main.services.ServiceDesguace;
import com.DesguaceExpress.main.services.ServiceSendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

@Service
public class ServiceDesguaceImpl implements ServiceDesguace {

    RepositoryDesguace repositoryDesguace;

    ServiceSendEmail serviceSendEmail;

    @Autowired
    MembersRepository membersRepository;

    @Autowired
    VehicleParkingRepository vehicleParkingRepository;

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    LocationRepository locationRepository;

    Logger logger = LoggerFactory.getLogger(ServiceDesguaceImpl.class);


    public ServiceDesguaceImpl(RepositoryPostgreImpl repositoryDesguace,
                               ServiceSendEmailImpl serviceSendEmail) {
        this.repositoryDesguace = repositoryDesguace;
        this.serviceSendEmail = serviceSendEmail;
    }

    @Override
    public List<Top10VehicleInParking> TopVehicleInParking() {
        return repositoryDesguace.TopVehicleInParking();
    }


    @Override
    public HashMap<String, Long> RegistrarEntrada(String licensePlate, Long idParking) {
        licensePlate=licensePlate.toUpperCase();
        VehicleParking vehicleParking = VehicleParking.builder()
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
        licensePlate=licensePlate.toUpperCase();
        VehicleParking vehicleParking = repositoryDesguace.findRegisterOpenByLicencePlate(licensePlate);
        //busca que exista un registro abierto del parqueadero
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
        //hace el calculo del costo por las horas que estubo en el parqueadero y lo guarda
        vehicleParking.setCost(
                ChronoUnit.HOURS.between(vehicleParking.getEntry(), vehicleParking.getExit())
                        *vehicleParking.getParkingId().getCostHour()
        );
        parkingRepository.save(vehicleParking.getParkingId());
        vehicleParkingRepository.save(vehicleParking);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id","salida registrada");
        return hashMap;
    }

    @Override
    public List<VehicleByParking> findVehiclesByParking(String parkingName) {
        Parking parking = repositoryDesguace.findParkingByName(parkingName);
        return repositoryDesguace.findVehicleByParkingId(parking.getId());
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
    public HashMap<String, String> callSendEmail(EmailBodyPre emailBodyPre) {
        emailBodyPre.setPlaca(emailBodyPre.getPlaca().toUpperCase());
        EmailBodySend emailBodySend = repositoryDesguace.VehicleInParkingByLicensePlate(emailBodyPre);
        return serviceSendEmail.SendEmail(emailBodySend);
    }

    @Override
    public List<Top10VehicleInParking> TopVehicleInParkingByParkingId(Long id) {
        return repositoryDesguace.TopVehicleInParkingByParkingId(id);
    }

    @Override
    public List<VehicleDetails> VehicleInParkingForTheFirstTime() {
        return repositoryDesguace.VehicleInParkingForTheFirstTime();
    }

    @Override
    public PeriodicEarnings findPeriodicEarningsByParkingId(Long id) {
        LocalDateTime hoy = LocalDateTime.now();
        PeriodicEarnings periodicEarnings = PeriodicEarnings.builder()
                .day(repositoryDesguace.FindEarningsByDate(id,hoy.minusDays(1L),hoy))
                .week(repositoryDesguace.FindEarningsByDate(id,hoy.minusWeeks(1L),hoy))
                .month(repositoryDesguace.FindEarningsByDate(id,hoy.minusMonths(1L),hoy))
                .year(repositoryDesguace.FindEarningsByDate(id,hoy.minusYears(1L),hoy))
                .build();
        return periodicEarnings;
    }

    @Override
    public MaximumIncome MaximumIncomeForDay(Long id) {
        return repositoryDesguace.MaximumIncomeForDay(id);
    }

    @Override
    public List<Top3Parking> Top3ParkingThisYear() {
        //saca la fecha actual
        LocalDateTime dateFin = LocalDateTime.now();
        //le resta un año
        LocalDateTime dateInit = dateFin.minusYears(1L);
        return repositoryDesguace.Top3ParkingThisYear(dateInit,dateFin);
    }

    @Override
    public List<VehicleByParking> VehiclesInAParkingByPartialData(PartialData partialData) {
        if(partialData.getParkingId()==null){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"id del parqueadero para la busqueda");
        }
        int indicador=0;
        indicador+=(partialData.getPartialLicensePlate()==null)?0:1;
        indicador+=(partialData.getDateInit()==null)?0:10;
        indicador+=(partialData.getDateEnd()==null)?0:100;
        if(indicador==000){//no proporciono ningun dato
            return repositoryDesguace.findVehicleByParkingIdAndDataPartial(
                    partialData.getParkingId()
            );
        }
        if(indicador==001){//solo proporciono la placa
            partialData.setPartialLicensePlate(partialData.getPartialLicensePlate().toUpperCase());
            return repositoryDesguace.findVehicleByParkingIdAndDataPartial(
                    partialData.getParkingId(),
                    partialData.getPartialLicensePlate()
            );
        }
        //formateador de fecha para que sea valida al momento de la ejecucion de la busqueda
        DateTimeFormatter formateador = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")).toFormatter();
        if(indicador==110){//solo proporciono las fechas
            return repositoryDesguace.findVehicleByParkingIdAndDataPartial(
                    partialData.getParkingId(),
                    LocalDateTime.parse(partialData.getDateInit()+" 00:00:00", formateador),
                    LocalDateTime.parse(partialData.getDateEnd()+" 00:00:00", formateador)
            );
        }

        if(indicador==111){//proporciono todos los datos
            partialData.setPartialLicensePlate(partialData.getPartialLicensePlate().toUpperCase());
            return repositoryDesguace.findVehicleByParkingIdAndDataPartial(
                    partialData.getParkingId(),
                    partialData.getPartialLicensePlate(),
                    LocalDateTime.parse(partialData.getDateInit()+" 00:00:00", formateador),
                    LocalDateTime.parse(partialData.getDateEnd()+" 00:00:00", formateador)
            );
        }
        throw new DataNotFound(HttpStatus.NOT_FOUND,"resultados, tiene que proporcionar ambas o ninguna fecha");
    }

    @Override
    public HashMap<String, String> LinMemberToParking(MemberToParking membertoparking) {
        if(repositoryDesguace.FindMemberInParking(membertoparking.getParkingId())!=0L){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"plaza disponible para el parqueadero");
        }
        if(repositoryDesguace.FindMemberInParkingsByMember(membertoparking.getMembersId())!=0){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"al socio disponible, ya esta registrado en otro parqueadero");
        }
        //busca socio correspondiente al id
        Members members = repositoryDesguace.findMemberById(membertoparking.getMembersId());
        //busca parking correspondiente al id
        Parking parking = repositoryDesguace.findParkingById(membertoparking.getParkingId());

        //guarda el socio en el parquin para guardarlo en la db
        parking.setMembersId(members);
        //guarda el parking actualizado con el nuevo socio
        parkingRepository.save(parking);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","el socio "+members.getFirstName()+" "+members.getFirstName()+" "+
                " fue asignado al parqueadero "+parking.getName());
        return hashMap;
    }

    @Override
    public HashMap<String, String> disconnectMemberToParking(Long parkingId) {
        //busca parking correspondiente al id
        Parking parking = repositoryDesguace.findParkingById(parkingId);
        if(parking.getMembersId()==null){
            throw new DataNotFound(HttpStatus.NOT_FOUND,"socio vinculado al parqueadero id "+parkingId);
        }
        parking.setMembersId(null);
        parkingRepository.save(parking);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se desvinculo socio del parqueadero id "+parkingId);
        return hashMap;
    }

    @Override
    public HashMap<String, String> RegisterMember(Members members) {
        //verifica que el documento no este en uso
        if(repositoryDesguace.FindIfDocumentIsInUse(members.getDocument(),members.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"documento ya esta en uso");
        }
        //verifica que el email no este en uso
        if(repositoryDesguace.FindIfEmailIsInUse(members.getEmail(),members.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"email ya esta en uso");
        }
        //verifica que el phone no este en uso
        if(repositoryDesguace.FindIfPhoneIsInUse(members.getPhone(),members.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"telefono ya esta en uso");
        }
        membersRepository.save(members);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","socio creado");
        return hashMap;
    }

    @Override
    public HashMap<String, String> UpdateMember(Members members) {
        Members members1 = repositoryDesguace.findMemberById(members.getId());

        if(repositoryDesguace.FindIfDocumentIsInUse(members.getDocument(),members.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"documento ya esta en uso");
        }
        if(repositoryDesguace.FindIfEmailIsInUse(members.getEmail(),members.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"email ya esta en uso");
        }
        if(repositoryDesguace.FindIfPhoneIsInUse(members.getPhone(),members.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"telefono ya esta en uso");
        }
        members.setCreateOn(members1.getCreateOn());
        membersRepository.save(members);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se actualizo el socio ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> DeleteMember(Long id) {
        //busca al socio
        Members members = repositoryDesguace.findMemberById(id);
        //busca los autos que le pertenece y los borra
        repositoryDesguace.FindVehicleIdByMemberId(id).forEach(x->
        {
            repositoryDesguace.FindVehicleParkingIdByMemberId(x).forEach(y->
            {
                //busca los registros y los borra
                vehicleParkingRepository.deleteById(y);
            });
            vehicleRepository.deleteById(x);
        });
        //busca si hay un parqueadero vinculado lo busca y deja el campo null
        Long parkingId = repositoryDesguace.FindMemberInParkingsByMember(members.getId());
        if(parkingId!=0){
            Parking parking = repositoryDesguace.findParkingById(parkingId);
            parking.setMembersId(null);
            parkingRepository.save(parking);
        }
        //elimina al socio
        membersRepository.delete(members);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se elimino el socio ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> RegisterVehicle(Vehicle vehicle) {
        vehicle.setLicensePlate(vehicle.getLicensePlate().toUpperCase());
        if(repositoryDesguace.FindIfLicensePlateIsInUse(vehicle.getLicensePlate(), vehicle.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"placa ya esta en uso");
        }
        Members members = repositoryDesguace.findMemberById(vehicle.getMembersId().getId());
        vehicle.setMembersId(members);
        vehicleRepository.save(vehicle);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se creo el vehiculo ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> DeleteVehicle(Long id) {
        repositoryDesguace.FindVehicleById(id);
        repositoryDesguace.FindVehicleParkingIdByMemberId(id).forEach(x->
        {
            //busca los registros y los borra
            vehicleParkingRepository.deleteById(x);
        });
        vehicleRepository.deleteById(id);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se elimino el vehiculo ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> RegisterParking(Parking parking) {
        if(parking.getMembersId()!=null){
            Members members = repositoryDesguace.findMemberById(parking.getMembersId().getId());
        }
        if(repositoryDesguace.FindIfParkingNameIsInUse(parking.getName(),parking.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"nombre del parqueadero ya esta en uso");
        }
        parking.setCurrentCapacity(0);
        parking.setMembersId(null);//se deja nulo y se añade por aparte el socio
        parking.setLocationId(repositoryDesguace.FindLocationById(parking.getLocationId().getId()));
        parkingRepository.save(parking);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se creo el nuevo parqueadero ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> UpdateParking(Parking parking) {
        Parking p = repositoryDesguace.FindParkingById(parking.getId());
        parking.setMembersId(p.getMembersId());//mantiene el socio
        parking.setCurrentCapacity(p.getCurrentCapacity());//mantiene los vehiculos
        parking.setCreateOn(p.getCreateOn());//mantiene la fecha de creacion
        parking.setLocationId(repositoryDesguace.FindLocationById(parking.getLocationId().getId()));//busca la locacion por id
        if(repositoryDesguace.FindIfParkingNameIsInUse(parking.getName(),parking.getId())){
            throw new DataIsInUse(HttpStatus.CONFLICT,"nombre del parquien ya esta en uso");
        }
        parkingRepository.save(parking);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se actualizo el parqueadero ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> DeleteParking(Long id) {
        repositoryDesguace.FindParkingById(id);
        parkingRepository.deleteById(id);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se elimino el parqueadero ");
        return hashMap;
    }

    @Override
    public HashMap<String, String> RegisterLocation(Location location) {
        locationRepository.save(location);
        HashMap<String, String> hashMap = new HashMap<>();
        //genera el mensaje correspondiente
        hashMap.put("mensaje","se añadio la locacion ");
        return hashMap;
    }
}
