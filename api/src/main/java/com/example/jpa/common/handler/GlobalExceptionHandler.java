package com.example.jpa.common.handler;

import com.example.jpa.common.exception.*;
import com.example.jpa.common.model.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailException.class)
    public ResponseEntity<?> authExceptionHandler(AuthFailException exception) {
        return ResponseEntity.status(401).body(ResponseResult.fail("[인증실패]" + exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedExceptionHandler(AccessDeniedException exception) {
        return ResponseEntity.status(403).body(ResponseResult.fail("[접근 거부]" + exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundExceptionHandler(NotFoundException exception) {
        return ResponseEntity.status(404).body(ResponseResult.fail("[조회 불가]" + exception.getMessage()));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> bizExceptionHandler(DuplicateException exception) {
        return ResponseEntity.status(409).body(ResponseResult.fail("[중복 오류]" + exception.getMessage()));
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<?> bizExceptionHandler(BizException exception) {
        return ResponseEntity.status(400).body(ResponseResult.fail("[서비스 오류]" + exception.getMessage()));
    }
}
