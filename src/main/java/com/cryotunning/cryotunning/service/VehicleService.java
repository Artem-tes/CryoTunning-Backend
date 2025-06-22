package com.cryotunning.cryotunning.service;

import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.requestdto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface VehicleService {
    ResponseEntity<?> getUserCars(UserDetails userDetails);
    ResponseEntity<?> createCar(UserDetails userDetails, CreateCarDTO dto);
    ResponseEntity<?> deleteCar(UserDetails userDetails,Integer idCar);
    ResponseEntity<CarResponseDTO> getCarById(User user,Integer idCar);
}
