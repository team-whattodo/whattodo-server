package me.cher1shrxd.whattodoserver.domain.user.dto.response;

import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;

public record ProjectResponse(
        String id,
        String title,
        String detail,
        String repository
) {
    public static ProjectResponse of(ProjectEntity projectEntity) {
        return new ProjectResponse(projectEntity.getId(), projectEntity.getTitle(), projectEntity.getTitle(), projectEntity.getRepository());
    }
}
