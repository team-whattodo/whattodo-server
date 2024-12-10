package me.cher1shrxd.whattodoserver.global.security.jwt.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
