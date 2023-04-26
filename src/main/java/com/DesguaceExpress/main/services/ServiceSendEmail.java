package com.DesguaceExpress.main.services;

import com.DesguaceExpress.main.dto.EmailBodySend;

import java.util.HashMap;

public interface ServiceSendEmail {

        /**
         * con la informacion de emailBodySend llama a un microservicio para que envie el email, de ser positivo
         * devuelve un 200 con el mensaje si es correcto, si no se puede conectar, tarta la respuesta o el mensaje
         * no es correcto genera un throw indicando el problema
         * @param emailBodySend email, palca, mensaje y parqueaderoName
         * @return mensaje que llega desde el microservicio
         */
        public HashMap<String, String> SendEmail(EmailBodySend emailBodySend);
}
