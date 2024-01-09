package com.example.jpa.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
