package com.choga3gan.delivery.global.jwt;

import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        int expireTime,
        String refreshToken,
        int expireRefreshTime
){

}
