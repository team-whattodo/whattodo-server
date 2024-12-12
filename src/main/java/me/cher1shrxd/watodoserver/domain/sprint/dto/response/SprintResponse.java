package me.cher1shrxd.watodoserver.domain.sprint.dto.response;

import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;

import java.util.List;

public record SprintResponse(
        String id,
        String title,
        String detail,
        String start,
        String deadline,
        List<TaskEntity> task
) {
}
