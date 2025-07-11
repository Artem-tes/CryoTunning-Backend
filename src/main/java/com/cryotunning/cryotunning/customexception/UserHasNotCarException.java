package com.cryotunning.cryotunning.customexception;

public class UserHasNotCarException extends RuntimeException {
    public UserHasNotCarException(String message) {
        super(message);
    }
}
