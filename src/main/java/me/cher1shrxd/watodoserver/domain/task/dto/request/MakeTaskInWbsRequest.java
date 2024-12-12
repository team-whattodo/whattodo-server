package me.cher1shrxd.watodoserver.domain.task.dto.request;

public record MakeTaskInWbsRequest(
        String title,
        String detail,
        String start,
        String deadline,
        String parentId
) {
}
