package com.choga3gan.delivery.user.dto;

public record UserUpdateRequest(
        String email,
        String password,
        boolean publicInfo

) {
}
