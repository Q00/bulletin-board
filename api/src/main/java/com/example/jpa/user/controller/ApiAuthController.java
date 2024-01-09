package com.example.jpa.user.controller;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.guard.CurrentUser;
import com.example.jpa.common.model.JwtToken;
import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.*;
import com.example.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class ApiAuthController {


    private final UserService userService;

    @PostMapping() // 본인 사용 중인 이메일의 회원가입
    public ResponseEntity<?> signIn(@RequestBody UserInput userInput) {

        ServiceResult result = userService.signIn(userInput);
        return ResponseResult.result(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseResult.fail("입력값이 정확하지 않습니다.");
        }

        JwtToken token = userService.login(userLogin);
        return ResponseResult.success(token);
    }

    @GetMapping()
    public ResponseEntity<?> getUser(@CurrentUser UserDetails user) {
        UserResponseDTO userDTO = userService.getUser(user.getUsername());
        return ResponseResult.success(userDTO);
    }
}
