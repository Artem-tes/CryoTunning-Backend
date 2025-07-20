package com.cryotunning.cryotunning.controllers.controlleradvices;

import com.cryotunning.cryotunning.customexception.userexception.IncorrectPasswordException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handlingIncorrectPasswordException(IncorrectPasswordException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
