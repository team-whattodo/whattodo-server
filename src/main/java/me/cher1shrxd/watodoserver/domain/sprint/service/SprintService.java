package me.cher1shrxd.watodoserver.domain.sprint.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.watodoserver.domain.schedule.entity.ScheduleEntity;
import me.cher1shrxd.watodoserver.domain.schedule.repository.ScheduleRepository;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.AddScheduleRequest;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ScheduleRepository scheduleRepository;
    private final ProjectRepository projectRepository;

    public void makeSprint(MakeSprintRequest makeSprintRequest, String projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
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

    public void addSchedule(AddScheduleRequest addScheduleRequest, String sprintId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(addScheduleRequest.scheduleId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.SCHEDULE_NOT_FOUND));

        SprintEntity sprintEntity = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        scheduleEntity.setSprint(sprintEntity);
        scheduleEntity.setDeadline(sprintEntity.getDeadline());
        scheduleEntity.setStart(sprintEntity.getStart());

        scheduleRepository.save(scheduleEntity);
        sprintEntity.setSchedules(List.of(scheduleEntity));
        sprintRepository.save(sprintEntity);
    }
}
