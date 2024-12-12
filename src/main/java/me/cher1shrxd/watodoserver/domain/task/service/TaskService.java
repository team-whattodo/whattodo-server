package me.cher1shrxd.watodoserver.domain.task.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.task.dto.request.MakeTaskInSprintRequest;
import me.cher1shrxd.watodoserver.domain.task.dto.request.MakeTaskInWbsRequest;
import me.cher1shrxd.watodoserver.domain.task.dto.request.RegisterBranchRequest;
import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;
import me.cher1shrxd.watodoserver.domain.task.repository.TaskRepository;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;
import me.cher1shrxd.watodoserver.domain.wbs.repository.WbsRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final WbsRepository wbsRepository;

    public void makeTaskInSprint(MakeTaskInSprintRequest makeTaskInSprintRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(makeTaskInSprintRequest.title())
                .detail(makeTaskInSprintRequest.detail())
                .build();

        SprintEntity sprintEntity = sprintRepository.findById(makeTaskInSprintRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        taskEntity.setStart(sprintEntity.getStart());
        taskEntity.setDeadline(sprintEntity.getDeadline());
        taskEntity.setSprint(sprintEntity);

        taskRepository.save(taskEntity);
    }

    public void makeTaskInWbs(MakeTaskInWbsRequest makeTaskInWbsRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(makeTaskInWbsRequest.title())
                .detail(makeTaskInWbsRequest.detail())
                .start(makeTaskInWbsRequest.start())
                .deadline(makeTaskInWbsRequest.deadline())
                .build();

        WbsEntity wbsEntity = wbsRepository.findById(makeTaskInWbsRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        taskEntity.setWbs(wbsEntity);

        taskRepository.save(taskEntity);
    }

    public void registerBranch(RegisterBranchRequest registerBranchRequest, String taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.TASK_NOT_FOUND));

        SprintEntity sprintEntity = taskEntity.getSprint();
        ProjectEntity projectEntity = sprintEntity.getProject();

        String repoName = projectEntity.getRepository();
        String branchName = registerBranchRequest.branchName();

        taskEntity.setBranch(repoName+":"+branchName);
        System.out.println(repoName+":"+branchName);
        taskRepository.save(taskEntity);
    }
}
