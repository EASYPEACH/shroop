package com.easypeach.shroop.modules.auth.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String msg){
        super(msg);
    }
}
