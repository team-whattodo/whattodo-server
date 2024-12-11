package me.cher1shrxd.whattodoserver.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.whattodoserver.domain.schedule.dto.request.MakeScheduleRequest;
import me.cher1shrxd.whattodoserver.domain.schedule.dto.request.RegisterBranchRequest;
import me.cher1shrxd.whattodoserver.domain.schedule.entity.ScheduleEntity;
import me.cher1shrxd.whattodoserver.domain.schedule.repository.ScheduleRepository;
import me.cher1shrxd.whattodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.whattodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.whattodoserver.domain.wbs.entity.WbsEntity;
import me.cher1shrxd.whattodoserver.domain.wbs.repository.WbsRepository;
import me.cher1shrxd.whattodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.whattodoserver.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final SprintRepository sprintRepository;
    private final WbsRepository wbsRepository;

    public void makeScheduleInSprint(MakeScheduleRequest makeScheduleRequest) {
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .title(makeScheduleRequest.title())
                .detail(makeScheduleRequest.detail())
                .start(makeScheduleRequest.start())
                .deadline(makeScheduleRequest.deadline())
                .build();

        SprintEntity sprintEntity = sprintRepository.findById(makeScheduleRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        scheduleEntity.setSprint(sprintEntity);

        scheduleRepository.save(scheduleEntity);
    }

    public void makeScheduleInWbs(MakeScheduleRequest makeScheduleRequest) {
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .title(makeScheduleRequest.title())
                .detail(makeScheduleRequest.detail())
                .start(makeScheduleRequest.start())
                .deadline(makeScheduleRequest.deadline())
                .build();

        WbsEntity wbsEntity = wbsRepository.findById(makeScheduleRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        scheduleEntity.setWbs(wbsEntity);

        scheduleRepository.save(scheduleEntity);
    }

    public void registerBranch(RegisterBranchRequest registerBranchRequest, String scheduleId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SCHEDULE_NOT_FOUND));

        SprintEntity sprintEntity = scheduleEntity.getSprint();
        ProjectEntity projectEntity = sprintEntity.getProject();

        String repoName = projectEntity.getRepository();
        String branchName = registerBranchRequest.branchName();

        scheduleEntity.setBranch(repoName+":"+branchName);
    }
}
