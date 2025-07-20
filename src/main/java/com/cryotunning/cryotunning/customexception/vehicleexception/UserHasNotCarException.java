package com.cryotunning.cryotunning.customexception.vehicleexception;

public class UserHasNotCarException extends RuntimeException {
    public UserHasNotCarException(String message) {
        super(message);
    }
}
