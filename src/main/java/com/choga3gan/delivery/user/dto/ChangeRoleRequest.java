package com.choga3gan.delivery.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeRoleRequest {

    @Schema(description = "역할 이름", example = "ROLE_USER")
    private String roleName;

    @Schema(description = "사용자 이름", example = "tester001")
    private String userName;
}
