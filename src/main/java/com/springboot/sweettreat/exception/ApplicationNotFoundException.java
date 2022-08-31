package com.springboot.sweettreat.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(String exception) {
        super(exception);
    }
}
