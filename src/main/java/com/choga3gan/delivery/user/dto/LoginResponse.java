package com.choga3gan.delivery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private String username;
}
