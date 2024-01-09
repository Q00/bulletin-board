package com.example.jpa.user.service;

import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.UserRole;
import com.example.jpa.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException(s + " -> 데이터베이스에서 찾을 수 없습니다."));
    }
}
