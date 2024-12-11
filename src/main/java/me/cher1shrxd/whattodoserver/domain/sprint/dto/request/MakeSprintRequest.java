package me.cher1shrxd.whattodoserver.domain.sprint.dto.request;

import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;

public record MakeSprintRequest(
        String title,
        String detail,
        String start,
        String deadline
) {
}
