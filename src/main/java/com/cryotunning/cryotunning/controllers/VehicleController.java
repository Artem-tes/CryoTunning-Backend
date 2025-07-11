package com.cryotunning.cryotunning.controllers;

import com.cryotunning.cryotunning.entities.User;
import com.cryotunning.cryotunning.entities.requestdto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.repository.UserRepository;
import com.cryotunning.cryotunning.service.VehicleService;
import com.cryotunning.cryotunning.service.servicesclass.CreateCarService;
import com.cryotunning.cryotunning.service.servicesclass.DeleteCarService;
import com.cryotunning.cryotunning.service.servicesclass.EmptyFastFailObject;
import com.cryotunning.cryotunning.service.servicesclass.GetCarsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final CreateCarService createCarService;
    private final GetCarsService getCarsService;
    private final DeleteCarService deleteCarService;


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
    public ResponseEntity<EmptyFastFailObject> deleteCar(@AuthenticationPrincipal
                                       User user, @PathVariable("id") Integer idCar){
        return deleteCarService.execute(new DeleteDto(idCar),user);

    }

    @GetMapping("/api/user/cars/{idAuto}")
    public ResponseEntity<CarResponseDTO> getUserCarByIdCar(@AuthenticationPrincipal User user,@PathVariable("idAuto") Integer idAuto){
        return vehicleService.getCarById(user,idAuto);
    }

}
