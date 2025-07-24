package com.cryotunning.cryotunning.controllers.vehiclecontroller;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.entities.responsesto.ErrorDTO;
import com.cryotunning.cryotunning.service.servicesclass.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Vehicle Controller",
        description = "Позволяет создавать, получать, удалять, получать по ID все авто")
public class VehicleController {

    private final CreateCarService createCarService;
    private final GetCarsService getCarsService;
    private final DeleteCarService deleteCarService;
    private final GetUserCarByIdService getUserCarByIdService;




    @Operation(
            summary = "Создание автомобиля",
            description = "Создает авто, без модификации, возвращает созданное авто",
            responses = {@ApiResponse(
                    description = "Авто успешно создано",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarResponseDTO.class)
                    ),
                    responseCode = "200"
            ),
                    @ApiResponse(
                            description = "В теле запроса некорректные данные",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorDTO.class
                                    )
                            ),
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorDTO.class
                                    )
                            ),
                            description = "У пользователя уже больше 6 созданных" +
                                    " авто, чтобы создать больше" +
                                    " - нужна подписка",
                            responseCode = "500"
                    )
            }
    )
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
