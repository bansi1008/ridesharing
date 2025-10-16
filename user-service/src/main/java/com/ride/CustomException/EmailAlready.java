package com.ride.CustomException;

public class EmailAlready extends RuntimeException {
    public EmailAlready(String email) {
        super("Email already exists: " + email);
    }
    
}
