package com.DesguaceExpress.main.Functionalities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * bean para activar RestTemplate para la coneccion mediante http con otro microservice
 */
@Component
public class Beans {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
