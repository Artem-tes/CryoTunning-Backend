package com.cryotunning.cryotunning.service;

import com.cryotunning.cryotunning.entities.requestdto.AuthDTO;
import org.springframework.http.ResponseEntity;

public interface AuthLoginService {
    ResponseEntity<String> regNewUser(AuthDTO authDTO);
    ResponseEntity<?> authAction(AuthDTO authDTO);
}
