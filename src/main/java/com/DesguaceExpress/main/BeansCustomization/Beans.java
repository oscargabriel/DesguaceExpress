package com.DesguaceExpress.main.BeansCustomization;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class Beans {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
