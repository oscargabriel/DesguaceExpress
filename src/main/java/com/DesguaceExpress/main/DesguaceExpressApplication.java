package com.DesguaceExpress.main;

import com.DesguaceExpress.main.repositories.jpa.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesguaceExpressApplication {
	public static void main(String[] args) {
		SpringApplication.run(DesguaceExpressApplication.class, args);

	}

}
