package com.cryotunning.cryotunning.controllers;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.service.servicesclass.*;
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
    private final GetUserCarByIdService getUserCarByIdService = new GetUserCarByIdService();



    //createCarService
    //POST /api/user/cars - создание нового авто
    //REQUEST TYPE - набор данных для создания(CreateCarDTO)
    //RETURN TYPE - сохраненое авто(CarResponseDTO)
    @PostMapping("/api/user/cars")
    public ResponseEntity<CarResponseDTO> createCar(@AuthenticationPrincipal User user,
                                       @Valid @RequestBody CreateCarDTO createCarDTO){
        return createCarService.execute(createCarDTO,user);
    }


    //getCarService
    //GET /api/user/cars
    //REQUEST TYPE - NONE
    //RETURN TYPE - List<CarResponseDTO>
    @GetMapping("/api/user/cars")
    public ResponseEntity<LinkedList<CarResponseDTO>> getCars(@AuthenticationPrincipal
                                         User user){
        return getCarsService.execute(user);
    }

    //deleteCarService
    //DELETE /api/user/cars/id(удаляемого авто)
    //REQUEST TYPE - DeleteDTO
    //RESPONSE TYPE - EMPTY,STATUS - 204
    @DeleteMapping("/api/user/cars/{id}")
    public ResponseEntity<?> deleteCar(@AuthenticationPrincipal
                                       User user, @PathVariable("id") Integer idCar){
        return deleteCarService.execute(new DeleteDto(idCar),user);

    }

    //getUserCarByIdService
    //GET /api/user/cars/34(id авто)
    //REQUEST TYPE - GetCarRequestDTO
    //RESPONSE TYPE - CarResponseDTO
    @GetMapping("/api/user/cars/{idAuto}")
    public ResponseEntity<CarResponseDTO> getUserCarByIdCar(@AuthenticationPrincipal User user,@PathVariable("idAuto") Integer idAuto){
        return getUserCarByIdService.execute(new GetCarRequestDTO(idAuto),user);
    }

}
