package com.cryotunning.cryotunning.controllers;

import com.cryotunning.cryotunning.entities.dbentities.User;
import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.service.AuthLoginService;
import com.cryotunning.cryotunning.service.servicesclass.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;


    @PostMapping("/api/registr")
    public ResponseEntity<?> registrUser(@Valid @RequestBody AuthDTO authDTO){
        return registrationService.execute(authDTO,new User());
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthDTO authDTO){

    }
}
