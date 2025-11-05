package com.choga3gan.delivery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class  LoginRequest{
    private String username;
    private String password;
}
