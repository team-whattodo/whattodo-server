package me.cher1shrxd.watodoserver.domain.sprint.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.watodoserver.domain.task.repository.TaskRepository;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;

    public void makeSprint(MakeSprintRequest makeSprintRequest) {
        ProjectEntity projectEntity = projectRepository.findById(makeSprintRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        SprintEntity sprintEntity = SprintEntity.builder()
                .title(makeSprintRequest.title())
                .detail(makeSprintRequest.detail())
                .start(makeSprintRequest.start())
                .deadline(makeSprintRequest.deadline())
                .project(projectEntity)
                .build();
        sprintRepository.save(sprintEntity);
    }
}
