package me.cher1shrxd.whattodoserver.domain.sprint.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.sprint.dto.request.AddScheduleRequest;
import me.cher1shrxd.whattodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.whattodoserver.domain.sprint.service.SprintService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sprint")
@Tag(name = "SPRINT")
public class SprintController {
    private final SprintService sprintService;

    @PostMapping
    public void makeSprint(@RequestBody MakeSprintRequest makeSprintRequest) {
        sprintService.makeSprint(makeSprintRequest);
    }

    @PostMapping("/{sprintId}")
    public void addSchedule(@RequestBody AddScheduleRequest addScheduleRequest, @PathVariable String sprintId) {
        sprintService.addSchedule(addScheduleRequest, sprintId);
    }
}