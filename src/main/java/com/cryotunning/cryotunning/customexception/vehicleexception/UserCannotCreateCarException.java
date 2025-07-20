package com.cryotunning.cryotunning.customexception.vehicleexception;

public class UserCannotCreateCarException extends RuntimeException {
    public UserCannotCreateCarException(String message) {
        super(message);
    }
}
