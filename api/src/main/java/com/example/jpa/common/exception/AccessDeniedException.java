package com.example.jpa.common.exception;

public class AccessDeniedException extends BizException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
