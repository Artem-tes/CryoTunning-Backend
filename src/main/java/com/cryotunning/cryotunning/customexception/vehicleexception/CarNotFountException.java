package com.cryotunning.cryotunning.customexception.vehicleexception;

public class CarNotFountException extends RuntimeException {
    public CarNotFountException(String message) {
        super(message);
    }
}
