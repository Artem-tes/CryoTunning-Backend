package com.cryotunning.cryotunning.controllers;

import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import com.cryotunning.cryotunning.entities.requestdto.ResponseLoginDTO;
import com.cryotunning.cryotunning.service.AuthLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthLoginService authLoginService;

    @PostMapping("/api/public/registr")
    public ResponseEntity<String> postAuthEndPoint(@Valid @RequestBody AuthDTO authDTO){
        return authLoginService.regNewUser(authDTO);
    }

    @PostMapping("/api/public/login")
    public ResponseEntity<?> postLoginEndPoint(@Valid @RequestBody AuthDTO authDTO){
        return authLoginService.authAction(authDTO);
    }
}
