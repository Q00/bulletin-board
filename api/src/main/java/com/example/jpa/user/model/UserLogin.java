package com.example.jpa.user.model;

import lombok.*;


@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    private String email;

    private String password;
}
