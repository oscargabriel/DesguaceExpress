package com.DesguaceExpress.main.controllers.Impl;

import com.DesguaceExpress.main.entities.Location;
import com.DesguaceExpress.main.functionalities.LexicalAnalyzer;
import com.DesguaceExpress.main.controllers.ControllerDesguace;
import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.services.Impl.ServiceDesguaceImpl;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
public class ControllerDesguaceImpl implements ControllerDesguace {

    Logger logger = LoggerFactory.getLogger(ControllerDesguaceImpl.class);

    ServiceDesguace serviceDesguace;

    LexicalAnalyzer lexicalAnalyzer=new LexicalAnalyzer();

    public ControllerDesguaceImpl(ServiceDesguaceImpl serviceDesguace) {
        this.serviceDesguace = serviceDesguace;
    }

    @Override
    @GetMapping("api/top10Vehiculos")
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParking() {
        return ResponseEntity.ok().body(serviceDesguace.TopVehicleInParking());
    }

    @Override
    @GetMapping("api/VehiculosEnParqueadero/{parkingName}")
    public ResponseEntity<List<VehicleByParking>> findVehiclesByParking(@PathVariable String parkingName) {
        lexicalAnalyzer.validateRegularExpression(parkingName,"parkingName",false);
        return ResponseEntity.ok().body(serviceDesguace.findVehiclesByParking(parkingName));
    }

    @Override
    @GetMapping("api/VehiculosDeSociosEnParqueaderos/{memberDocument}")
    public ResponseEntity<List<VehicleInParkingByMembers>> findVehiclesByMember(@PathVariable String memberDocument) {
        lexicalAnalyzer.validateRegularExpression(memberDocument,"document",false);
        return ResponseEntity.ok().body(serviceDesguace.findVehiclesByMember(memberDocument));
    }

    @Override
    @GetMapping("api/DetallesDelVehiculo/{id}")
    public ResponseEntity<VehicleDetails> findVehicleDetailsById(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.findVehicleDetailsById(id));
    }

    @Override
    @PostMapping("api/service/EnviarEmail")
    public ResponseEntity<HashMap<String, String>> callSendEmail(@RequestBody EmailBodyPre emailBodySend) {
        lexicalAnalyzer.validateRegularExpression(emailBodySend.getEmail(),"email",false);
        lexicalAnalyzer.validateRegularExpression(emailBodySend.getPlaca(),"licensePlate",false);
        lexicalAnalyzer.validateRegularExpression(emailBodySend.getParqueaderoId(),"id",false);
        return ResponseEntity.ok().body(serviceDesguace.callSendEmail(emailBodySend));
    }

    @Override
    @GetMapping("api/top10VehiculosEnParqueaderos/{id}")
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParkingByParkingId(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.TopVehicleInParkingByParkingId(id));
    }

    @Override
    @GetMapping("api/VehiculosEnParqueaderos")
    public ResponseEntity<List<VehicleDetails>> VehicleInParkingForTheFirstTime() {
        return ResponseEntity.ok().body(serviceDesguace.VehicleInParkingForTheFirstTime());
    }

    @Override
    @GetMapping("api/gananciaPorPeriodos/{id}")
    public ResponseEntity<PeriodicEarnings> findPeriodicEarningsByParkingId(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.findPeriodicEarningsByParkingId(id));
    }

    @Override
    @GetMapping("api/gananciaMaxima/{id}")
    public ResponseEntity<MaximumIncome> MaximumIncomeForDay(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.MaximumIncomeForDay(id));
    }

    @Override
    @GetMapping("api/top3parqueaderos")
    public ResponseEntity<List<Top3Parking>> Top3ParkingThisYear() {
        return ResponseEntity.ok().body(serviceDesguace.Top3ParkingThisYear());
    }

    @Override
    @GetMapping("api/DatosParciales")
    public ResponseEntity<List<VehicleByParking>> VehiclesInAParkingByPartialData(@RequestBody PartialData partialData) {
        lexicalAnalyzer.validateRegularExpression(partialData.getParkingId(),"id",false);
        lexicalAnalyzer.validateRegularExpression(partialData.getDateEnd(),"date",true);
        lexicalAnalyzer.validateRegularExpression(partialData.getDateInit(),"date",true);
        lexicalAnalyzer.validateRegularExpression(partialData.getPartialLicensePlate(),"partiaLicensePlate",true);
        return ResponseEntity.ok().body(serviceDesguace.VehiclesInAParkingByPartialData(partialData));
    }

    @Override
    @PostMapping("socios/crear")
    public ResponseEntity<HashMap<String, String>> RegisterMember(@RequestBody Members members) {
        lexicalAnalyzer.validateRegularExpression(members.getEmail(),"email",false);
        lexicalAnalyzer.validateRegularExpression(members.getDocument(),"document",false);
        lexicalAnalyzer.validateRegularExpression(members.getPhone(),"phone",false);
        lexicalAnalyzer.validateRegularExpression(members.getLastName(),"lastName",false);
        lexicalAnalyzer.validateRegularExpression(members.getFirstName(),"fistName",false);
        return ResponseEntity.ok().body(serviceDesguace.RegisterMember(members));
    }

    @Override
    @PutMapping("socios/actualizar")
    public ResponseEntity<HashMap<String, String>> UpdateMember(@RequestBody Members members) {
        lexicalAnalyzer.validateRegularExpression(members.getId(),"id",false);
        lexicalAnalyzer.validateRegularExpression(members.getEmail(),"email",false);
        lexicalAnalyzer.validateRegularExpression(members.getDocument(),"document",false);
        lexicalAnalyzer.validateRegularExpression(members.getPhone(),"phone",false);
        lexicalAnalyzer.validateRegularExpression(members.getLastName(),"lastName",false);
        lexicalAnalyzer.validateRegularExpression(members.getFirstName(),"fistName",false);
        return ResponseEntity.ok().body(serviceDesguace.UpdateMember(members));
    }

    @Override
    @DeleteMapping("socios/eliminar/{id}")
    public ResponseEntity<HashMap<String, String>> DeleteMember(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.DeleteMember(id));
    }

    @Override
    @PostMapping("vehiculo/crear")
    public ResponseEntity<HashMap<String, String>> RegisterVehicle(@RequestBody Vehicle vehicle) {
        lexicalAnalyzer.validateRegularExpression(vehicle.getModel(),"model",false);
        lexicalAnalyzer.validateRegularExpression(vehicle.getMake(),"make",false);
        lexicalAnalyzer.validateRegularExpression(vehicle.getType(),"type",false);
        lexicalAnalyzer.validateRegularExpression(vehicle.getYear(),"year",false);
        lexicalAnalyzer.validateRegularExpression(vehicle.getLicensePlate(),"licensePlate",false);
        lexicalAnalyzer.validateRegularExpression(vehicle.getMembersId().getId(),"id",false);
        return ResponseEntity.ok().body(serviceDesguace.RegisterVehicle(vehicle));
    }

    @Override
    @DeleteMapping("vehiculo/eliminar/{id}")
    public ResponseEntity<HashMap<String, String>> DeleteVehicle(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.DeleteVehicle(id));
    }

    @Override
    @PostMapping("vehiculo/registrarEntrada")
    public ResponseEntity<HashMap<String, Long>> RegistrarEntrada(@RequestBody Tiket tiket) {
        lexicalAnalyzer.validateRegularExpression(tiket.getLicencePlate(),"licensePlate",false);
        lexicalAnalyzer.validateRegularExpression(tiket.getParkingId(),"parkingId",false);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                serviceDesguace.RegistrarEntrada(tiket.getLicencePlate(), tiket.getParkingId())
        );
    }

    @Override
    @PutMapping("vehiculo/registrarSalida")
    public ResponseEntity<HashMap<String, String>> RegistrarSalida(@RequestBody Tiket tiket) {
        lexicalAnalyzer.validateRegularExpression(tiket.getLicencePlate(),"licensePlate",false);
        lexicalAnalyzer.validateRegularExpression(tiket.getParkingId(),"parkingId",false);
        return ResponseEntity.ok().body(
                serviceDesguace.RegistrarSalida(tiket.getLicencePlate(),tiket.getParkingId())
        );
    }

    @Override
    @PostMapping("parqueadero/vincularSocio")
    public ResponseEntity<HashMap<String, String>> LinkMemberToParking(@RequestBody MemberToParking membertoparking) {
        lexicalAnalyzer.validateRegularExpression(membertoparking.getMembersId(),"id",false);
        lexicalAnalyzer.validateRegularExpression(membertoparking.getParkingId(),"id",false);
        return ResponseEntity.ok().body(serviceDesguace.LinMemberToParking(membertoparking));
    }

    @Override
    @PutMapping("parqueadero/desvincularSocio")
    public ResponseEntity<HashMap<String, String>> disconnectMemberToParking(@RequestBody HashMap<String, Long> parkingId) {
        lexicalAnalyzer.validateRegularExpression(parkingId.get("parkingId"),"parkingId",false);
        return ResponseEntity.ok().body(serviceDesguace.disconnectMemberToParking(parkingId.get("parkingId")));
    }

    @Override
    @PostMapping("parqueadero/crear")
    public ResponseEntity<HashMap<String, String>> RegisterParking(@RequestBody Parking parking) {
        lexicalAnalyzer.validateRegularExpression(parking.getName(),"parkingName",false);
        lexicalAnalyzer.validateRegularExpression(parking.getMaxCapacity(),"maxCapacity",false);
        lexicalAnalyzer.validateRegularExpression(parking.getCostHour(),"costHour",false);
        lexicalAnalyzer.validateRegularExpression(parking.getLocationId().getId(),"locationId",false);
        return ResponseEntity.ok().body(serviceDesguace.RegisterParking(parking));
    }

    @Override
    @PutMapping("parqueadero/actualizar")
    public ResponseEntity<HashMap<String, String>> UpdateParking(@RequestBody Parking parking) {
        lexicalAnalyzer.validateRegularExpression(parking.getId(),"id",false);
        lexicalAnalyzer.validateRegularExpression(parking.getName(),"parkingName",false);
        lexicalAnalyzer.validateRegularExpression(parking.getMaxCapacity(),"maxCapacity",false);
        lexicalAnalyzer.validateRegularExpression(parking.getCostHour(),"costHour",false);
        return ResponseEntity.ok().body(serviceDesguace.UpdateParking(parking));
    }

    @Override
    @DeleteMapping("parqueadero/eliminar/{id}")
    public ResponseEntity<HashMap<String, String>> DeleteParking(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(id,"id",false);
        return ResponseEntity.ok().body(serviceDesguace.DeleteParking(id));
    }

    @Override
    @PostMapping("ubicacion/crear")
    public ResponseEntity<HashMap<String, String>> RegisterLocation(@RequestBody Location location) {
        lexicalAnalyzer.validateRegularExpression(location.getState(),"state",false);
        lexicalAnalyzer.validateRegularExpression(location.getCountry(),"country",false);
        lexicalAnalyzer.validateRegularExpression(location.getUbication(),"ubication",false);
        lexicalAnalyzer.validateRegularExpression(location.getDepartament(),"departament",false);
        return ResponseEntity.ok().body(serviceDesguace.RegisterLocation(location));
    }
}
