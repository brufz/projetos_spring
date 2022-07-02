package com.api.parkingcontrol1.controller;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol1.dtos.ParkingSpotDto;
import com.api.parkingcontrol1.model.ParkingSpotModel;
import com.api.parkingcontrol1.services.ParkingSpotService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
	//controller recebe as solicitações (get, put, post, delete...), aciona o service que vai acionar o repository 
	//que fará as transações com a base de dados
	
	final ParkingSpotService parkingSpotService;

	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}
	
	//responseEntity para montar uma resposta
	// Object porque podem ter diferentes tipos de retorno dependendo da situação
	// @Valid valida as anotações do dto
	
	@PostMapping
	//validações
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
		if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Licence car is already in use");
		}
		
		if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Spot Number is already in use");
		}
		
		if(parkingSpotService.existsByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already registered for this apartament/block");
		}
		
		var parkingSpotModel = new ParkingSpotModel(); //conversao de dto para model
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}
	
	@GetMapping
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot(){
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ParkingSpotModel> getById(@PathVariable UUID id){
		return parkingSpotService.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) //lambda function
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public void delete (@PathVariable UUID id){
		parkingSpotService.deleteById(id);
	}
	
	//@GetMapping("/{id}")
	//public ResponseEntity<Object> getById(@PathVariable (value = "id") UUID id){
		//Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		//if(!parkingSpotModelOptional.isPresent()) {
			//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
		//}
		//return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	//}
	
	
	
	
	
	
	
}
