package com.choga3gan.delivery.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class  LoginRequest{

    @Schema(description = "유저 ID", example = "tester001")
    private String username;

    @Schema(description = "비밀번호", example = "Tester001!")
    private String password;
}
