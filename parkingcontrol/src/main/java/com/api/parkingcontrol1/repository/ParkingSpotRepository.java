package com.api.parkingcontrol1.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol1.model.ParkingSpotModel;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{

public boolean existsByLicensePlateCar(String licensePlateCar);
public boolean existsByParkingSpotNumber(String parkingSpotNumber);
public boolean existsByApartamentAndBlock(String apartament, String block);

}
