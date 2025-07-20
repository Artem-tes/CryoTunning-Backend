package com.cryotunning.cryotunning.controllers.controlleradvices;

import com.cryotunning.cryotunning.customexception.userexception.UsernameIsOwningException;
import com.cryotunning.cryotunning.customexception.vehicleexception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleControllerAdvice {
    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<String> handlingHullFieldException(){
        return ResponseEntity.badRequest().body("Cannot resolve fields from request");
    }
    @ExceptionHandler(NullFindCarInfoException.class)
    public ResponseEntity<String> handlingNullFieldCarInfoException(NullFindCarInfoException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CarNotFountException.class)
    public ResponseEntity<String> handleNotCarFountException(CarNotFountException carNotFountException){
        return ResponseEntity.badRequest().body(carNotFountException.getMessage());
    }

    @ExceptionHandler(UserHasNotCarException.class)
    public ResponseEntity<String> handleUserHasNotCarException(UserHasNotCarException userHasNotCarException){
        return ResponseEntity.status(401).body(userHasNotCarException.getMessage());
    }

    @ExceptionHandler(UserCannotCreateCarException.class)
    public ResponseEntity<String> handlingUserCannotCreateCarException(UserCannotCreateCarException createCarException){
        return ResponseEntity.badRequest().body(createCarException.getMessage());
    }

    @ExceptionHandler(UsernameIsOwningException.class)
    public ResponseEntity<String> handlingUsernameIsOwningException(UsernameIsOwningException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
