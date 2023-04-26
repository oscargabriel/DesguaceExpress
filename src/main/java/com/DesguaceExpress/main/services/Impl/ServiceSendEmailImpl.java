package com.DesguaceExpress.main.services.Impl;

import com.DesguaceExpress.main.dto.EmailBodySend;
import com.DesguaceExpress.main.exception.custom.EmailOutOfService;
import com.DesguaceExpress.main.services.ServiceSendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


/**
 * Servicio que se encarga de establecer coneccion con el microServicio de enviar email
 */
@Service
public class ServiceSendEmailImpl implements ServiceSendEmail {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HashMap<String, String> SendEmail(EmailBodySend emailBodySend) {

        ResponseEntity<HashMap> responseEntity=null;
        try {
            //1: url, 2: body, 3: estructura de la respuesta
            responseEntity = restTemplate.postForEntity(
                    "http://localhost:8081/api/sendEmail", emailBodySend, HashMap.class
            );
            //verifica que el mensaje corresponda al esperado
            if("correo enviado".equalsIgnoreCase((String) responseEntity.getBody().get("mensaje"))){
                return responseEntity.getBody();
            }
            //caso contrario genera un throw
            throw new EmailOutOfService(HttpStatus.NOT_FOUND,"Servicio en drogas");
        }catch (ResourceAccessException e){//atrapa la excepcion por no poder hacer coneccion
            throw new EmailOutOfService(HttpStatus.NOT_FOUND,"No se pudo establecer coneccion, intente mas tarde");
        }
        catch (HttpClientErrorException e){//atrapa la excepcion por tiempo de respuesta agotado
            throw new EmailOutOfService(HttpStatus.NOT_FOUND,"Tiempo de espera agotado, intente mas tarde");
        }
    }

}

