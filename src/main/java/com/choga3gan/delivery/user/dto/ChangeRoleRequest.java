package com.choga3gan.delivery.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeRoleRequest {
    private String roleName;
    private String userName;
}
