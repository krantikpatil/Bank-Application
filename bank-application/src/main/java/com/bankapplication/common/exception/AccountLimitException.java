package com.bankapplication.common.exception;

public class AccountLimitException extends RuntimeException{
    public AccountLimitException(String message) {
        super(message);
    }
}
