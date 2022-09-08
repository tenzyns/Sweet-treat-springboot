package com.springboot.sweettreat.exception;

public class CourierNotFoundException extends RuntimeException {
    public CourierNotFoundException(String exception) {
        super(exception);
    }
}
