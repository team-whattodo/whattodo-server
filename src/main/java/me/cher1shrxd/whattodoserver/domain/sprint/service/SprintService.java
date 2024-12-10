package me.cher1shrxd.whattodoserver.domain.sprint.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.schedule.entity.ScheduleEntity;
import me.cher1shrxd.whattodoserver.domain.schedule.repository.ScheduleRepository;
import me.cher1shrxd.whattodoserver.domain.sprint.dto.request.AddScheduleRequest;
import me.cher1shrxd.whattodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.whattodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.whattodoserver.domain.sprint.repository.SprintRepository;
import me.cher1shrxd.whattodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.whattodoserver.global.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ScheduleRepository scheduleRepository;

    public void makeSprint(MakeSprintRequest makeSprintRequest) {
        SprintEntity sprintEntity = SprintEntity.builder()
                .title(makeSprintRequest.title())
                .detail(makeSprintRequest.detail())
                .build();
        sprintRepository.save(sprintEntity);
    }

    public void addSchedule(AddScheduleRequest addScheduleRequest, String sprintId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(sprintId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SCHEDULE_NOT_FOUND));

        SprintEntity sprintEntity = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        scheduleEntity.setSprint(sprintEntity);
        scheduleEntity.setDeadline(sprintEntity.getDeadline());
        scheduleEntity.setStart(sprintEntity.getStart());

        sprintEntity.setSchedules(List.of(scheduleEntity));
        sprintRepository.save(sprintEntity);
    }
}
