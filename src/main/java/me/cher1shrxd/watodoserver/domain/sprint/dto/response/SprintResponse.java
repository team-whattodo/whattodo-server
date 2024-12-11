package me.cher1shrxd.watodoserver.domain.sprint.dto.response;

public record SprintResponse(
        String id,
        String title,
        String detail,
        String start,
        String deadline
) {
}
