package me.cher1shrxd.whattodoserver.domain.schedule.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.schedule.dto.request.MakeScheduleRequest;
import me.cher1shrxd.whattodoserver.domain.schedule.dto.request.RegisterBranchRequest;
import me.cher1shrxd.whattodoserver.domain.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Tag(name = "SCHEDULE")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public void makeSchedule(@RequestBody MakeScheduleRequest makeScheduleRequest) {
        scheduleService.makeSchedule(makeScheduleRequest);
    }

    @PostMapping("/{scheduleId}")
    public void registerBranch(@RequestBody RegisterBranchRequest registerBranchRequest, @PathVariable String scheduleId) {
        scheduleService.registerBranch(registerBranchRequest, scheduleId);
    }
}
