package me.cher1shrxd.watodoserver.domain.task.dto.request;

public record EditTaskInWbsRequest(
        String title,
        String start,
        String deadline,
        String branch
) {
}
