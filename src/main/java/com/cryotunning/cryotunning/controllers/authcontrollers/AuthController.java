package com.cryotunning.cryotunning.controllers.authcontrollers;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.service.servicesclass.LoginService;
import com.cryotunning.cryotunning.service.servicesclass.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
description = "Позволяет зарегестрироватся и входить")
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

    @PostMapping("/api/public/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthDTO authDTO){
        return loginService.execute(authDTO,new User());
    }
}
