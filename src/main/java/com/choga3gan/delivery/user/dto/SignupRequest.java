package com.choga3gan.delivery.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest (

        @Schema(description = "사용자 ID", example = "tester001")
        @NotBlank(message = "아이디는 필수 입력값 입니다.")
        @Size(min=4, max=10, message = "아이디는 4자 이상 10자 이하로 입력해주세요.")
        @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 소문자와 숫자만 사용할 수 있습니다.")
        String username,

        @Schema(description = "사용자 이메일", example = "tester001@test.com")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "사용자 비밀번호", example = "Tester001!")
        @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
        @Size(min=8, max=15)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
                message = "비밀번호는 대소문자, 숫자, 특수문자를 모두 포함해야 합니다."
        )
        String password,

        @Schema(description = "사용자 비밀번호 확인", example = "Tester001!")
        @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
        @Size(min=8, max=15)
        String confirmPassword
){}
