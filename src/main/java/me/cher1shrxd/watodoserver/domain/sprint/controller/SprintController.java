package me.cher1shrxd.watodoserver.domain.sprint.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.EditSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.dto.response.SprintResponse;
import me.cher1shrxd.watodoserver.domain.sprint.service.SprintService;
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

    @DeleteMapping("/{sprintId}")
    public void deleteSprint(@PathVariable String sprintId) {
        sprintService.deleteSprint(sprintId);
    }

    @PatchMapping("/{sprintId}")
    public SprintResponse editSprint(@RequestBody EditSprintRequest editSprintRequest, @PathVariable String sprintId) {
        return sprintService.editSprint(editSprintRequest, sprintId);
    }

}
