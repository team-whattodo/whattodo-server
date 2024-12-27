package me.cher1shrxd.watodoserver.domain.user.dto.request;

public record UserUpdateRequest(
        String username,
        String nickname,
        String pat,
        String password,
        String currentPassword
) {
}
