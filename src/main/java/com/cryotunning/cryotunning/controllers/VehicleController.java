package com.cryotunning.cryotunning.controllers;

import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.requestdto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.repository.UserRepository;
import com.cryotunning.cryotunning.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/api/user/cars")
    public ResponseEntity<?> getCars(@AuthenticationPrincipal
                                         UserDetails userDetails){
        return vehicleService.getUserCars(userDetails);
    }

    @PostMapping("/api/user/cars")
    public ResponseEntity<?> createNewCar(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CreateCarDTO createCarDTO){
        return vehicleService.createCar(userDetails,createCarDTO);
    }

    @DeleteMapping("/api/user/cars/{id}")
    public ResponseEntity<?> deleteCar(@AuthenticationPrincipal
                                       UserDetails userDetails, @PathVariable("id") Integer idCar){
        return vehicleService.deleteCar(userDetails,idCar);

    }

    @GetMapping("/api/user/cars/{idAuto}")
    public ResponseEntity<CarResponseDTO> getUserCarByIdCar(@AuthenticationPrincipal User user,@PathVariable("idAuto") Integer idAuto){
        return vehicleService.getCarById(user,idAuto);
    }

}
