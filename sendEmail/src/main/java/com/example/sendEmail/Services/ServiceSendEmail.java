package com.example.sendEmail.Services;

import com.example.sendEmail.entities.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

/**
 * servicio que recibe la informacion con los datos para enviar un email y lo imprime mediante logger
 * para hacer la simulacion y devuelve un mensaje si la ejecucion fue exitosa
 */
@Service
public class ServiceSendEmail {

    Logger logger = LoggerFactory.getLogger(ServiceSendEmail.class);

    public HashMap<String,String> sendEmailMS(Email email){
        HashMap<String,String> resp = new HashMap<>();
        logger.info(email.toString());
        resp.put("mensaje","correo enviado");
        return resp;
    }
}
