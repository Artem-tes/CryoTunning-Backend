package com.cryotunning.cryotunning.customexception.vehicleexception;

public class CarIsOwningModification extends RuntimeException {
    public CarIsOwningModification(String message) {
        super(message);
    }
}
