package com.example.sendEmail.Controllers;

import com.example.sendEmail.Services.ServiceSendEmail;
import com.example.sendEmail.entities.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

/**
 * microservicio para enviar un email, recibe la informacion por parametros y la envia a Services para que
 * se encargue de la ejecucion
 */
@Controller
public class ControllerSendEmail {

    @Autowired
    ServiceSendEmail sendEmail;

    /**
     * funcion que recibe la informacion por body para enviar un mensaje, envia la informacion a service y
     * retorna la informaicon que recibe de services para enviarla al microservicio que la llamo
     * TODO: que solo se pueda recibir solicitudes desde el microservicio
     * @param email String email; private placa; String mensaje; String parqueaderoNombre;
     * @return HashMap con la informacion
     */
    @PostMapping("api/sendEmail")
    public ResponseEntity<HashMap<String,String>> sendEmailMS(@RequestBody Email email){
        return ResponseEntity.ok().body(sendEmail.sendEmailMS(email));
    }



}
