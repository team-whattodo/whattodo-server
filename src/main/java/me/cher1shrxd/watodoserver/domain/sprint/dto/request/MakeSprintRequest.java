package me.cher1shrxd.watodoserver.domain.sprint.dto.request;

public record MakeSprintRequest(
        String title,
        String detail,
        String start,
        String deadline,
        String parentId
) {
}
