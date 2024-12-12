package me.cher1shrxd.watodoserver.domain.task.dto.response;

public record TaskResponse(
        String id,
        String title,
        String detail,
        String branch,
        boolean isDone,
        String start,
        String deadline
) {
}
