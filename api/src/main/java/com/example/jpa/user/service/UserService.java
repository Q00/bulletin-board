package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.model.JwtToken;
import com.example.jpa.user.model.*;


public interface UserService {

    /**
     * 로그인 정보 확인
     * */
    JwtToken login(UserLogin userLogin);

    /**
     * 회원 가입
     */
    ServiceResult signIn(UserInput userInput);

	/**
	 * 회원 정보 조회
	 */
	UserResponseDTO getUser(String email);
}
