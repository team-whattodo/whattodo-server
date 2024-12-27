package me.cher1shrxd.watodoserver.domain.task.dto.request;

public record MakeTaskInSprintRequest(
    String title,
    String branch,
    String parentId
) {
}
