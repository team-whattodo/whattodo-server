package me.cher1shrxd.watodoserver.domain.project.dto.response;

import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectMemberEntity;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;

import java.util.List;

public record ProjectDetailResponse(
        String id,
        String title,
        String detail,
        String repository,
        SprintEntity sprint,
        WbsEntity wbs,
        List<UserEntity> members
) {
    public static ProjectDetailResponse of(ProjectEntity projectEntity) {
        return new ProjectDetailResponse(
                projectEntity.getId(),
                projectEntity.getTitle(),
                projectEntity.getDetail(),
                projectEntity.getRepository(),
                projectEntity.getSprint(),
                projectEntity.getWbs(),
                projectEntity.getMembers().stream()
                        .map(ProjectMemberEntity::getUser)
                        .toList()
        );
    }
}
