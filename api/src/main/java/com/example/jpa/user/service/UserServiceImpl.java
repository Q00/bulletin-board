package com.example.jpa.user.service;

import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.common.exception.AuthFailException;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.exception.DuplicateException;
import com.example.jpa.common.exception.NotFoundException;
import com.example.jpa.common.model.JwtToken;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.*;
import com.example.jpa.common.guard.JwtTokenProvider;
import com.example.jpa.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtToken login(UserLogin userLogin) {

        Optional<User> optionalUser = userRepository.findByEmail(userLogin.getEmail());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("회원 정보가 존재하지 않습니다.");
        }
        User getUser = optionalUser.get();

        if (!passwordEncoder.matches(userLogin.getPassword(), getUser.getPassword())) {
            throw new NotFoundException("일치하는 정보가 없습니다.");
        }

        JwtToken t = jwtTokenProvider.generateToken(getUser);

        return jwtTokenProvider.generateToken(getUser);
    }

    @Override
    public ServiceResult signIn(UserInput userInput) {

        Optional<User> optionalUser = userRepository.findByEmail(userInput.getEmail());
        if (optionalUser.isPresent()) {
            throw new DuplicateException("이미 가입된이메일이 있습니다.");
        }

		User user = User.builder()
                .email(userInput.getEmail())
                .password(passwordEncoder.encode(userInput.getPassword()))
                .userName(userInput.getEmail())
                .regDate(LocalDateTime.now())
                .role(Collections.singleton(UserRole.NORMAL))
                .build();

        userRepository.save(user);

        return ServiceResult.success();
    }

	@Override
	public UserResponseDTO getUser(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (!optionalUser.isPresent()) {
			throw new NotFoundException("회원 정보가 존재하지 않습니다.");
		}
		UserResponseDTO user = UserResponseDTO.of(optionalUser.get());

		return user;
	}
}
