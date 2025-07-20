package com.cryotunning.cryotunning.customexception.userexception;

public class UsernameIsOwningException extends RuntimeException {
    public UsernameIsOwningException(String message) {
        super(message);
    }
}
