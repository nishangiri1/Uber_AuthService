package com.auth.uber_authservice.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String msg)
    {
        super(msg);
    }
}
