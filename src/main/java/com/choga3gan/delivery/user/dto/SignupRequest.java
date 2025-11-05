package com.choga3gan.delivery.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest (
        @NotBlank @Size(min=4, max=16) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min=8) String password,
        @NotBlank String confirmPassword
){}
