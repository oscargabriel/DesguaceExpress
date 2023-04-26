package com.DesguaceExpress.main.controllers.Impl;

import com.DesguaceExpress.main.Functionalities.LexicalAnalyzer;
import com.DesguaceExpress.main.controllers.ControllerDesguace;
import com.DesguaceExpress.main.dto.*;
import com.DesguaceExpress.main.entities.Members;
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
    @PostMapping("socios/register")
    public ResponseEntity<String> CrearSocio(@RequestBody Members members) {
        return ResponseEntity.ok().body(serviceDesguace.crearSocio(members));
    }





}
