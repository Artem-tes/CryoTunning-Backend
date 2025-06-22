package com.cryotunning.cryotunning.controllers.exccontrollers;

import com.cryotunning.cryotunning.customexception.CarNotFountException;
import com.cryotunning.cryotunning.customexception.NullFieldException;
import com.cryotunning.cryotunning.customexception.NullFindCarInfoException;
import com.cryotunning.cryotunning.customexception.UserHasNotCarException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SaveCarControllerAdvice {
    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<String> handlingHullFieldException(){
        return ResponseEntity.badRequest().body("Cannot resolve fields from request");
    }
    @ExceptionHandler(NullFindCarInfoException.class)
    public ResponseEntity<String> handlingNullFieldCarInfoException(){
        return ResponseEntity.internalServerError().body("Car data bad operate from DB");
    }

    @ExceptionHandler(CarNotFountException.class)
    public ResponseEntity<String> handleNotCarFountException(CarNotFountException carNotFountException){
        return ResponseEntity.badRequest().body(carNotFountException.getMessage());
    }

    @ExceptionHandler(UserHasNotCarException.class)
    public ResponseEntity<String> handleUserHasNotCarException(UserHasNotCarException userHasNotCarException){
        return ResponseEntity.status(401).body(userHasNotCarException.getMessage());
    }
}
