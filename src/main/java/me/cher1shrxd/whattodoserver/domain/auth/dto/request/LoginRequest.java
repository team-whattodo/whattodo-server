package me.cher1shrxd.whattodoserver.domain.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
