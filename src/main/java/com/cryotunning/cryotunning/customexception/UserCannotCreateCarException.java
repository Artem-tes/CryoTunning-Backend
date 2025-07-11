package com.cryotunning.cryotunning.customexception;

public class UserCannotCreateCarException extends RuntimeException {
    public UserCannotCreateCarException(String message) {
        super(message);
    }
}
