package com.cryotunning.cryotunning.controllers.vehiclecontroller;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.service.servicesclass.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final CreateCarService createCarService;
    private final GetCarsService getCarsService;
    private final DeleteCarService deleteCarService;
    private final GetUserCarByIdService getUserCarByIdService;



    @PostMapping("/api/user/cars")
    public ResponseEntity<CarResponseDTO> createCar(@AuthenticationPrincipal User user,
                                       @Valid @RequestBody CreateCarDTO createCarDTO){
        return createCarService.execute(createCarDTO,user);
    }


    @GetMapping("/api/user/cars")
    public ResponseEntity<LinkedList<CarResponseDTO>> getCars(@AuthenticationPrincipal
                                         User user){
        return getCarsService.execute(user);
    }

    @DeleteMapping("/api/user/cars/{id}")
    public ResponseEntity<?> deleteCar(@AuthenticationPrincipal
                                       User user, @PathVariable("id")Integer id){
        return deleteCarService.execute(new DeleteDto(id),user);

    }

    @GetMapping("/api/user/cars/{idAuto}")
    public ResponseEntity<CarResponseDTO> getUserCarByIdCar(@AuthenticationPrincipal User user,@PathVariable("idAuto") Integer idAuto){
        return getUserCarByIdService.execute(new GetCarRequestDTO(idAuto),user);
    }

}
