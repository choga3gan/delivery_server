package com.choga3gan.delivery.user.dto;

import com.choga3gan.delivery.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 값 반환 DTO")
public record UserResponse (

    String id,
    String username,
    String email,
    String rolename
){
    public static  UserResponse from(User user){
        String id = user.getId() != null && user.getId().getId() != null
                ? user.getId().getId().toString()
                : null;
        String roleName = user.getRole() != null ? user.getRole().getRoleName() : null;

        return new UserResponse(
                id,
                user.getUsername(),
                user.getEmail(),
                roleName
        );
    }
}
