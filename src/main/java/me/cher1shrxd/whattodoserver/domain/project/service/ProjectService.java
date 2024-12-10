package me.cher1shrxd.whattodoserver.domain.project.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.AddSprintRequest;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.AddWbsRequest;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.MakeProjectRequest;
import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.whattodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.whattodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.whattodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.whattodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.whattodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.whattodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.whattodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final UserRepository userRepository;

    public void makeProject(MakeProjectRequest makeProjectRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = ProjectEntity.builder()
                .title(makeProjectRequest.title())
                .detail(makeProjectRequest.detail())
                .repository(makeProjectRequest.repository())
                .members(List.of(userEntity))
                .build();

        projectRepository.save(projectEntity);
    }

    public void addSprint(AddSprintRequest addSprintRequest, String projectId) {
        SprintEntity sprintEntity = sprintRepository.findById(addSprintRequest.sprintId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        projectEntity.setSprint(sprintEntity);
        projectRepository.save(projectEntity);
    }

    public void addWbs(AddWbsRequest addWbsRequest, String projectId) {
        SprintEntity wbsEntity = sprintRepository.findById(addWbsRequest.wbsId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.WBS_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        projectEntity.setSprint(wbsEntity);
        projectRepository.save(projectEntity);
    }
}
