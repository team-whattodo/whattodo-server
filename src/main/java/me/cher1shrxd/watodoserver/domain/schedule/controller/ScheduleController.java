package me.cher1shrxd.watodoserver.domain.schedule.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.schedule.dto.request.MakeScheduleInSprintRequest;
import me.cher1shrxd.watodoserver.domain.schedule.dto.request.MakeScheduleInWbsRequest;
import me.cher1shrxd.watodoserver.domain.schedule.dto.request.RegisterBranchRequest;
import me.cher1shrxd.watodoserver.domain.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Tag(name = "SCHEDULE")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/sprint")
    public void makeScheduleInSprint(@RequestBody MakeScheduleInSprintRequest makeScheduleInSprintRequest) {
        scheduleService.makeScheduleInSprint(makeScheduleInSprintRequest);
    }

    @PostMapping("/wbs")
    public void makeScheduleInWbs(@RequestBody MakeScheduleInWbsRequest makeScheduleInWbsRequest) {
        scheduleService.makeScheduleInWbs(makeScheduleInWbsRequest);
    }


    @PostMapping("/{scheduleId}")
    public void registerBranch(@RequestBody RegisterBranchRequest registerBranchRequest, @PathVariable String scheduleId) {
        scheduleService.registerBranch(registerBranchRequest, scheduleId);
    }
}
