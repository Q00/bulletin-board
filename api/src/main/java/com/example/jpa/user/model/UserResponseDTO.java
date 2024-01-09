package com.example.jpa.user.model;

import com.example.jpa.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class UserResponseDTO {

    private long id;
    private String email;
    private String userName;

    public static UserResponseDTO of(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .build();
    }
}
