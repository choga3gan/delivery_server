package com.choga3gan.delivery.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "역할 생성 및 수정 요청 DTO")
public class RoleDto {

    @Schema(description = "역할 이름", example = "ROLE_USER")
    @NotBlank
    private String roleName;

    @Schema(description = "역할 설명", example = "기본 사용자")
    private String roleDescription;

    @Builder
    public RoleDto(String roleName, String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

}
