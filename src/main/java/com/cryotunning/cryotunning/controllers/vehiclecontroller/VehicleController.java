package com.cryotunning.cryotunning.controllers.vehiclecontroller;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.responsesto.CarResponseDTO;
import com.cryotunning.cryotunning.entities.requestdto.CreateCarDTO;
import com.cryotunning.cryotunning.entities.requestdto.DeleteDto;
import com.cryotunning.cryotunning.entities.requestdto.GetCarRequestDTO;
import com.cryotunning.cryotunning.entities.responsesto.ErrorDTO;
import com.cryotunning.cryotunning.repository.userpackage.UserRepository;
import com.cryotunning.cryotunning.service.servicesclass.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с машинами",
        description = "Позволяет создавать, получать, удалять, получать по ID все авто")
public class VehicleController {

    private final CreateCarService createCarService;
    private final GetCarsService getCarsService;
    private final DeleteCarService deleteCarService;
    private final GetUserCarByIdService getUserCarByIdService;
    private final UserRepository userRepository;




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
    public ResponseEntity<CarResponseDTO> createCar(@AuthenticationPrincipal UserDetails userDetails,
                                                    @Valid @RequestBody CreateCarDTO createCarDTO){
        return createCarService.execute(createCarDTO,userRepository.findByUsername(userDetails.getUsername()));
    }


    @Operation(
            summary = "Получение всех машин пользователя",
            description = "По запросу возвращается список из все машин пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешное выполнение",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CarResponseDTO.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                        description = "У пользователя нет машин",
                        responseCode = "400",
                        content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                        )
                    )
            }
    )
    @GetMapping("/api/user/cars")
    public ResponseEntity<LinkedList<CarResponseDTO>> getCars(@AuthenticationPrincipal
                                         UserDetails userDetails){
        return getCarsService.execute(
                userRepository.findByUsername(userDetails.getUsername())
        );
    }


    @Operation(
            summary = "Удаление авто",
            description = "Удаляет авто принадлежащее пользователю",
            responses = {
                    @ApiResponse(
                            description = "Авто успешно удалено",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ),
                            description = "Id из пути некорректное",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ),
                            description = "Машина с указанным ID находится не у пользователя под влиянием",
                            responseCode = "403")
            }
    )
    @DeleteMapping("/api/user/cars/{id}")
    public ResponseEntity<?> deleteCar(@AuthenticationPrincipal
                                       UserDetails user,
                                       @Parameter(name = "id",
                                       example = "132",
                                       description = "ID авто для удаления")
    @PathVariable("id")Integer id){
        return deleteCarService.execute(new DeleteDto(id),userRepository.findByUsername(user.getUsername()));

    }



    @Operation(
            summary = "Получение авто по ID",
            description = "Возвращает ваше авто с указанным в параметре ID",
            responses = {
                @ApiResponse(
                        description = "Успешное получение авто",
                        responseCode = "200",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CarResponseDTO.class)
                        )
                ),
                @ApiResponse(
                        description = "Указанный в пути ID некорректный",
                        responseCode = "400",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorDTO.class)
                        )
                ),
                @ApiResponse(
                        description = "Указанное в пути ID не принадлежит текущему пользователю",
                        responseCode = "403",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorDTO.class)
                        )
                )
            }
    )
    @GetMapping("/api/user/cars/{id}")
    public ResponseEntity<CarResponseDTO> getUserCarByIdCar(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Parameter(name = "id",description = "ID авто для удаления",example = "123") @PathVariable("idAuto") Integer idAuto){
        return getUserCarByIdService.execute(new GetCarRequestDTO(idAuto),userRepository.findByUsername(userDetails.getUsername()));
    }

}
