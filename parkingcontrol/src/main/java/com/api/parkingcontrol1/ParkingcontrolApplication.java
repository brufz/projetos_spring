package com.api.parkingcontrol1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkingcontrolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingcontrolApplication.class, args);
	
	}
	
	

}
