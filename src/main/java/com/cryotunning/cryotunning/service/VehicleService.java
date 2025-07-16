package com.cryotunning.cryotunning.service;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface VehicleService {
    ResponseEntity<?> getUserCars(UserDetails userDetails);
    ResponseEntity<?> createCar(User user, CreateCarDTO dto);
    ResponseEntity<?> deleteCar(User user,Integer idCar);
    ResponseEntity<CarResponseDTO> getCarById(User user,Integer idCar);
}
