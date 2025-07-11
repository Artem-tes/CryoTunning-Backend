package com.cryotunning.cryotunning.customexception;

public class UserHaveNotCarsException extends RuntimeException {
    public UserHaveNotCarsException(String message) {
        super(message);
    }
}
