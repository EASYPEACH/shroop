package com.easypeach.shroop.modules.auth.exception;

public class TokenExpirationException extends RuntimeException{
    public TokenExpirationException() {
        super();
    }

    public TokenExpirationException(String msg){
        super(msg);
    }
}
