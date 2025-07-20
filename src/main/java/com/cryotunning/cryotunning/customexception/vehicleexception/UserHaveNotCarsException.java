package com.cryotunning.cryotunning.customexception.vehicleexception;

public class UserHaveNotCarsException extends RuntimeException {
    public UserHaveNotCarsException(String message) {
        super(message);
    }
}
