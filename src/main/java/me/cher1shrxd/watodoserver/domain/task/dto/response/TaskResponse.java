package me.cher1shrxd.watodoserver.domain.task.dto.response;

import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;

public record TaskResponse(
        String id,
        String title,
        String branch,
        boolean isDone,
        String start,
        String deadline
) {
    public static TaskResponse of(TaskEntity taskEntity) {
        return new TaskResponse(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getBranch(),
                taskEntity.isDone(),
                taskEntity.getStart(),
                taskEntity.getDeadline()
        );
    }
}
