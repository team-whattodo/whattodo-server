package me.cher1shrxd.watodoserver.domain.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
