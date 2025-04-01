package com.bankapplication.common.exception;

public class AccountNotExistException extends RuntimeException{
    public AccountNotExistException(String message) {
        super(message);
    }
}
