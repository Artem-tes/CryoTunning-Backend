package com.cryotunning.cryotunning.controllers.authcontrollers;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.entities.responsesto.CompleteAuthDTO;
import com.cryotunning.cryotunning.entities.responsesto.ErrorDTO;
import com.cryotunning.cryotunning.service.servicesclass.LoginService;
import com.cryotunning.cryotunning.service.servicesclass.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Контроллер для регистрации и авторизации",
description = "Позволяет регестрировать нового пользователя, и авторизовываться")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginService loginService;

    @Operation(summary = "Регистрация пользователя",
    description = "Создает и добавляет польователя в базу данных" +
            "и шифрует пароль",
    responses = {
            @ApiResponse(
                    content = @Content(mediaType = "application/json"),
                    responseCode = "204",
                    description = "Пользователь успешно зарегестрирован"),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(mediaType = "application/json"),
                    description = "Username указаный в теле запроса уже занят")
    })
    @PostMapping("/api/public/registr")
    public ResponseEntity<?> registrUser(@Valid @RequestBody AuthDTO authDTO){
        return registrationService.execute(authDTO,new User());
    }


    @Operation(
            summary = "Авторизация пользователя",
            description = "Проверяет данные пользователя и если они корректны, выдает JWT токен, для дальнейшей автоматической авторизации",
            responses = {
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CompleteAuthDTO.class)),
                            responseCode = "200",
                            description = "Пользователь успешно вошел в учетную запись"
                            ),
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDTO.class)
                            ),
                            responseCode = "400",
                            description = "Учетной записи не существует"),
                    @ApiResponse(content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    ),
                            responseCode = "400",
                            description = "Неправильный пароль к учетной записи"
                    )
            }
    )
    @PostMapping("/api/public/login")
    public ResponseEntity<CompleteAuthDTO> loginUser(@Valid @RequestBody
                                                         @Parameter(name = "AuthDTO",
                                                                 description = "Хранит логин и пароль",
                                                                 content = @Content(
                                                                 mediaType = "application/json",
                                                                 schema = @Schema(implementation = AuthDTO.class)
                                                         )) AuthDTO authDTO){
        return loginService.execute(authDTO,new User());
    }
}
