package me.cher1shrxd.watodoserver.domain.sprint.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.EditSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.dto.response.SprintResponse;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void makeSprint(MakeSprintRequest makeSprintRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(makeSprintRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getId().equals(userEntity.getId()));

        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        SprintEntity sprintEntity = SprintEntity.builder()
                .title(makeSprintRequest.title())
                .detail(makeSprintRequest.detail())
                .start(makeSprintRequest.start())
                .deadline(makeSprintRequest.deadline())
                .project(projectEntity)
                .build();
        sprintRepository.save(sprintEntity);
    }

    public SprintResponse editSprint(EditSprintRequest editSprintRequest, String sprintId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        SprintEntity sprintEntity = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = sprintEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getId().equals(userEntity.getId()));

        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        String oldStart = sprintEntity.getStart();
        String oldDeadline = sprintEntity.getDeadline();

        if(editSprintRequest.title() != null) sprintEntity.setTitle(editSprintRequest.title());
        if(editSprintRequest.detail() != null) sprintEntity.setDetail(editSprintRequest.detail());
        if(editSprintRequest.start() != null) sprintEntity.setStart(editSprintRequest.start());
        if(editSprintRequest.deadline() != null) sprintEntity.setDeadline(editSprintRequest.deadline());

        if (!oldStart.equals(sprintEntity.getStart()) || !oldDeadline.equals(sprintEntity.getDeadline())) {
            sprintEntity.getTasks().forEach(task -> {
                task.setStart(sprintEntity.getStart());
                task.setDeadline(sprintEntity.getDeadline());
            });
        }

        sprintRepository.save(sprintEntity);

        return SprintResponse.of(sprintEntity);
    }

    public void deleteSprint(String sprintId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        SprintEntity sprintEntity = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = sprintEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getId().equals(userEntity.getId()));

        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        sprintRepository.deleteById(sprintId);
    }
}
