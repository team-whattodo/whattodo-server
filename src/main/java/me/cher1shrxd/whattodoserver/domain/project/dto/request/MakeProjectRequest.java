package me.cher1shrxd.whattodoserver.domain.project.dto.request;

public record MakeProjectRequest(
        String title,
        String detail,
        String repository
) {
}
