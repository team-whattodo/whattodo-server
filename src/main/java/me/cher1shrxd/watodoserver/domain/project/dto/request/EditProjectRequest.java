package me.cher1shrxd.watodoserver.domain.project.dto.request;

public record EditProjectRequest(
        String title,
        String detail,
        String repository
) {
}
