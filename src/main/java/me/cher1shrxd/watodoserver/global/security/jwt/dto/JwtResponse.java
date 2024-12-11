package me.cher1shrxd.watodoserver.global.security.jwt.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
