package com.cryotunning.cryotunning.controllers.controlleradvices;

import com.cryotunning.cryotunning.customexception.userexception.IncorrectPasswordException;
import com.cryotunning.cryotunning.entities.responsesto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorDTO> handlingIncorrectPasswordException(IncorrectPasswordException e){
        return ResponseEntity.status(400).body(new ErrorDTO(400,e.getMessage()));
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlingUsernameNotFoundException(UsernameNotFoundException e){
        return ResponseEntity.status(400).body(new ErrorDTO(400,e.getMessage()));
    }
}
