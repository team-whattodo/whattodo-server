package me.cher1shrxd.watodoserver.domain.task.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.github.service.GithubService;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.sprint.dto.response.SprintResponse;
import me.cher1shrxd.watodoserver.domain.task.dto.request.*;
import me.cher1shrxd.watodoserver.domain.task.dto.response.DeleteTaskResponse;
import me.cher1shrxd.watodoserver.domain.task.dto.response.TaskResponse;
import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;
import me.cher1shrxd.watodoserver.domain.task.repository.TaskRepository;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.watodoserver.domain.wbs.dto.response.WbsResponse;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;
import me.cher1shrxd.watodoserver.domain.wbs.repository.WbsRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final WbsRepository wbsRepository;
    private final UserRepository userRepository;
    private final GithubService githubService;

    public SprintResponse makeTaskInSprint(MakeTaskInSprintRequest makeTaskInSprintRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        SprintEntity sprintEntity = sprintRepository.findById(makeTaskInSprintRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = sprintEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        String repoName = projectEntity.getRepository();
        String branchName = githubService.getBranch(repoName, makeTaskInSprintRequest.branch(), userEntity.getPat());
        String connector = repoName+":"+branchName;

        boolean isAlreadyRegistered = taskRepository.existsByBranch(connector);

        if (isAlreadyRegistered) {
            throw new CustomException(CustomErrorCode.BRANCH_ALREADY_CONNECTED);
        }

        TaskEntity taskEntity = TaskEntity.builder()
                .title(makeTaskInSprintRequest.title())
                .branch(connector)
                .build();

        taskEntity.setStart(sprintEntity.getStart());
        taskEntity.setDeadline(sprintEntity.getDeadline());
        taskEntity.setSprint(sprintEntity);

        taskRepository.save(taskEntity);

        return SprintResponse.of(sprintEntity);
    }

    public WbsResponse makeTaskInWbs(MakeTaskInWbsRequest makeTaskInWbsRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        WbsEntity wbsEntity = wbsRepository.findById(makeTaskInWbsRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = wbsEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        String repoName = projectEntity.getRepository();
        String branchName = githubService.getBranch(repoName, makeTaskInWbsRequest.branch(), userEntity.getPat());
        String connector = repoName+":"+branchName;

        boolean isAlreadyRegistered = taskRepository.existsByBranch(connector);

        if (isAlreadyRegistered) {
            throw new CustomException(CustomErrorCode.BRANCH_ALREADY_CONNECTED);
        }

        TaskEntity taskEntity = TaskEntity.builder()
                .title(makeTaskInWbsRequest.title())
                .branch(connector)
                .start(makeTaskInWbsRequest.start())
                .deadline(makeTaskInWbsRequest.deadline())
                .build();

        taskEntity.setWbs(wbsEntity);

        taskRepository.save(taskEntity);

        return WbsResponse.of(wbsEntity);
    }

    public DeleteTaskResponse deleteTask(String taskId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.TASK_NOT_FOUND));

        SprintEntity sprintEntity = taskEntity.getSprint();
        WbsEntity wbsEntity = taskEntity.getWbs();

        if(sprintEntity != null) {
            ProjectEntity projectEntity = sprintEntity.getProject();

            boolean isMember = projectEntity.getMembers().stream()
                    .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


            if(!isMember) {
                throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
            }
        }else{
            ProjectEntity projectEntity = wbsEntity.getProject();

            boolean isMember = projectEntity.getMembers().stream()
                    .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


            if(!isMember) {
                throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
            }
        }

        taskRepository.deleteById(taskId);

        return DeleteTaskResponse.of(sprintEntity, wbsEntity);
    }



    public SprintResponse editTaskInSprint(EditTaskInSprintRequest editTaskInSprintRequest, String taskId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.TASK_NOT_FOUND));

        SprintEntity sprintEntity = taskEntity.getSprint();

        ProjectEntity projectEntity = sprintEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        String repoName = projectEntity.getRepository();
        String branchName = githubService.getBranch(repoName, editTaskInSprintRequest.branch(), userEntity.getPat());
        String connector = repoName+":"+branchName;

        boolean isAlreadyRegistered = taskRepository.existsByBranch(connector);

        if (isAlreadyRegistered) {
            throw new CustomException(CustomErrorCode.BRANCH_ALREADY_CONNECTED);
        }

        if(editTaskInSprintRequest.title() != null) taskEntity.setTitle(editTaskInSprintRequest.title());
        if(editTaskInSprintRequest.branch() != null) taskEntity.setBranch(connector);

        taskRepository.save(taskEntity);

        return SprintResponse.of(sprintEntity);
    }

    public WbsResponse editTaskInWbs(EditTaskInWbsRequest editTaskInWbsRequest, String taskId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.TASK_NOT_FOUND));

        WbsEntity wbsEntity = taskEntity.getWbs();

        ProjectEntity projectEntity = wbsEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        String repoName = projectEntity.getRepository();
        String branchName = githubService.getBranch(repoName, editTaskInWbsRequest.branch(), userEntity.getPat());
        String connector = repoName+":"+branchName;

        String oldBranch = taskEntity.getBranch();

        boolean isAlreadyRegistered = taskRepository.existsByBranch(connector);

        if (isAlreadyRegistered && !Objects.equals(oldBranch, connector)) {
            throw new CustomException(CustomErrorCode.BRANCH_ALREADY_CONNECTED);
        }

        if (editTaskInWbsRequest.title() != null) taskEntity.setTitle(editTaskInWbsRequest.title());
        if (editTaskInWbsRequest.start() != null) taskEntity.setStart(editTaskInWbsRequest.start());
        if (editTaskInWbsRequest.deadline() != null) taskEntity.setDeadline(editTaskInWbsRequest.deadline());
        if (editTaskInWbsRequest.branch() != null) taskEntity.setBranch(connector);

        taskRepository.save(taskEntity);

        return WbsResponse.of(wbsEntity);
    }

    public TaskResponse getTaskDetail(String taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.TASK_NOT_FOUND));

        return TaskResponse.of(taskEntity);
    }
}
