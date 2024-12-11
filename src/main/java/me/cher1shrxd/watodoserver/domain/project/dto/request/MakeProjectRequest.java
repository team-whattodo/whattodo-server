package me.cher1shrxd.watodoserver.domain.project.dto.request;

public record MakeProjectRequest(
        String title,
        String detail,
        String repository
) {
}
