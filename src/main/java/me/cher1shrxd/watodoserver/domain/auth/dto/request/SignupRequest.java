package me.cher1shrxd.watodoserver.domain.auth.dto.request;

public record SignupRequest(
        String username,
        String email,
        String nickname,
        String password,
        String pat
) {
}
