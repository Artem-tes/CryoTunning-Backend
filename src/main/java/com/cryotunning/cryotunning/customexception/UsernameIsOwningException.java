package com.cryotunning.cryotunning.customexception;

public class UsernameIsOwningException extends RuntimeException {
    public UsernameIsOwningException(String message) {
        super(message);
    }
}
