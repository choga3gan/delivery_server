package com.choga3gan.delivery.user.dto;

public record UserCreateRequest(
        String username,
        String email,
        String password,
        String roleName,
        Boolean publicInfo
) {}
