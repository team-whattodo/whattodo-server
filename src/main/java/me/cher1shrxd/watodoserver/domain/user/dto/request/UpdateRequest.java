package me.cher1shrxd.watodoserver.domain.user.dto.request;

public record UpdateRequest(
        String username,
        String nickname,
        String password,
        String currentPassword
) {
}