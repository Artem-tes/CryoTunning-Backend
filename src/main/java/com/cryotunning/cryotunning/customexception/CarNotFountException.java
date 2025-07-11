package com.cryotunning.cryotunning.customexception;

public class CarNotFountException extends RuntimeException {
    public CarNotFountException(String message) {
        super(message);
    }
}
