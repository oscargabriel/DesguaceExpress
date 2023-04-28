package com.DesguaceExpress.main.controllers.Impl;

import com.DesguaceExpress.main.Functionalities.LexicalAnalyzer;
import com.DesguaceExpress.main.controllers.ControllerDesguace;
import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Members;
import com.DesguaceExpress.main.entities.Parking;
import com.DesguaceExpress.main.entities.Vehicle;
import com.DesguaceExpress.main.services.Impl.ServiceDesguaceImpl;
import com.DesguaceExpress.main.services.ServiceDesguace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("top10Vehicle")
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParking() {
        return ResponseEntity.ok().body(serviceDesguace.TopVehicleInParking());
    }

    @Override
    @PostMapping("vehiculo/registrarEntrada")
    public ResponseEntity<HashMap<String, Long>> RegistrarEntrada(@RequestBody Tiket tiket) {
        lexicalAnalyzer.validateRegularExpression(tiket.getLicencePlate(),"licensePlate");
        lexicalAnalyzer.validateRegularExpression(String.valueOf(tiket.getIdParking()),"id");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                serviceDesguace.RegistrarEntrada(tiket.getLicencePlate(), tiket.getIdParking())
        );
    }

    @Override
    @PostMapping("vehiculo/registrarSalida")
    public ResponseEntity<HashMap<String, String>> RegistrarSalida(@RequestBody Tiket tiket) {
        lexicalAnalyzer.validateRegularExpression(tiket.getLicencePlate(),"licensePlate");
        lexicalAnalyzer.validateRegularExpression(String.valueOf(tiket.getIdParking()),"id");
        return ResponseEntity.ok().body(
                serviceDesguace.RegistrarSalida(tiket.getLicencePlate(),tiket.getIdParking())
        );
    }

    @Override
    @GetMapping("VehicleByParking")
    public ResponseEntity<List<VehicleByParking>> findVehiclesByParking(String parkingName) {
        lexicalAnalyzer.validateRegularExpression(parkingName,"parkingName");
        return ResponseEntity.ok().body(serviceDesguace.findVehiclesByParking(parkingName));
    }

    @Override
    @GetMapping("VehicleInParkingByMembers")
    public ResponseEntity<List<VehicleInParkingByMembers>> findVehiclesByMember(String memberDocument) {
    lexicalAnalyzer.validateRegularExpression(memberDocument,"document");
        return ResponseEntity.ok().body(serviceDesguace.findVehiclesByMember(memberDocument));
    }

    @Override
    @GetMapping("VehicleDetailsById/{id}")
    public ResponseEntity<VehicleDetails> findVehicleDetailsById(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(String.valueOf(id),"id");
        return ResponseEntity.ok().body(serviceDesguace.findVehicleDetailsById(id));
    }

    @Override
    @PostMapping("service/sendEmail")
    public ResponseEntity<HashMap<String, String>> callSendEmail(@RequestBody EmailBodyPre emailBodySend) {
        lexicalAnalyzer.validateRegularExpression(emailBodySend.getEmail(),"email");
        lexicalAnalyzer.validateRegularExpression(emailBodySend.getPlaca(),"licensePlate");
        lexicalAnalyzer.validateRegularExpression(String.valueOf(emailBodySend.getParqueaderoId()),"id");
        return ResponseEntity.ok().body(serviceDesguace.callSendEmail(emailBodySend));
    }

    @Override
    @GetMapping("top10VehicleInParking/{id}")
    public ResponseEntity<List<Top10VehicleInParking>> TopVehicleInParkingByParkingId(@PathVariable Long id) {
        lexicalAnalyzer.validateRegularExpression(String.valueOf(id),"id");
        return ResponseEntity.ok().body(serviceDesguace.TopVehicleInParkingByParkingId(id));
    }

    @Override
    public ResponseEntity<List<VehicleDetails>> VehicleInParkingForTheFirstTime() {
        return ResponseEntity.ok().body(serviceDesguace.VehicleInParkingForTheFirstTime());
    }

    @Override
    public ResponseEntity<PeriodicEarnings> findPeriodicEarningsByParkingId(Long id) {
        lexicalAnalyzer.validateRegularExpression(String.valueOf(id),"id");
        return ResponseEntity.ok().body(serviceDesguace.findPeriodicEarningsByParkingId(id));
    }

    @Override
    public ResponseEntity<MaximumIncome> MaximumIncomeForDay(Long id) {
        lexicalAnalyzer.validateRegularExpression(String.valueOf(id),"id");
        return ResponseEntity.ok().body(serviceDesguace.MaximumIncomeForDay(id));
    }

    @Override
    public ResponseEntity<List<Top3Parking>> Top3ParkingThisYear() {
        return ResponseEntity.ok().body(serviceDesguace.Top3ParkingThisYear());
    }

    @Override
    @GetMapping("partialData")
    public ResponseEntity<List<VehicleByParking>> VehiclesInAParkingByPartialData(@RequestBody PartialData partialData) {
        return ResponseEntity.ok().body(serviceDesguace.VehiclesInAParkingByPartialData(partialData));
    }

    @Override

    public ResponseEntity<HashMap<String, String>> LinMemberToParking(@RequestBody MemberToParking membertoparking) {
        return ResponseEntity.ok().body(serviceDesguace.LinMemberToParking(membertoparking));
    }

    @Override
    @PostMapping("socios/crear")
    public ResponseEntity<HashMap<String, String>> RegisterMember(@RequestBody Members members) {
        return ResponseEntity.ok().body(serviceDesguace.RegisterMember(members));
    }

    @Override
    @PutMapping("socios/actualizar")
    public ResponseEntity<HashMap<String, String>> UpdateMember(Members members) {
        return ResponseEntity.ok().body(serviceDesguace.UpdateMember(members));
    }

    @Override
    @DeleteMapping("socios/eliminar")
    public ResponseEntity<HashMap<String, String>> DeleteMember(Long id) {
        return ResponseEntity.ok().body(serviceDesguace.DeleteMember(id));
    }

    @Override
    @PostMapping("vehiculo/crear")
    public ResponseEntity<HashMap<String, String>> RegisterVehicle(Vehicle vehicle) {
        return ResponseEntity.ok().body(serviceDesguace.RegisterVehicle(vehicle));
    }


    @Override
    @DeleteMapping("vehiculo/eliminar")
    public ResponseEntity<HashMap<String, String>> DeleteVehicle(Long id) {
        return null;
    }

    @Override
    @DeleteMapping("parqueadero/eliminar")
    public ResponseEntity<HashMap<String, String>> RegisterParking(Parking parking) {
        return null;
    }

    @Override
    @DeleteMapping("parqueadero/eliminar")
    public ResponseEntity<HashMap<String, String>> UpdateParking(Parking parking) {
        return null;
    }

    @Override
    @DeleteMapping("parqueadero/eliminar")
    public ResponseEntity<HashMap<String, String>> DeleteParking(Long id) {
        return null;
    }
}
