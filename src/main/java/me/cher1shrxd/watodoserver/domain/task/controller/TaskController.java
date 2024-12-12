package me.cher1shrxd.watodoserver.domain.task.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.task.dto.request.MakeTaskInSprintRequest;
import me.cher1shrxd.watodoserver.domain.task.dto.request.MakeTaskInWbsRequest;
import me.cher1shrxd.watodoserver.domain.task.dto.request.RegisterBranchRequest;
import me.cher1shrxd.watodoserver.domain.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "TASK")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/sprint")
    public void makeTaskInSprint(@RequestBody MakeTaskInSprintRequest makeTaskInSprintRequest) {
        taskService.makeTaskInSprint(makeTaskInSprintRequest);
    }

    @PostMapping("/wbs")
    public void makeTaskInWbs(@RequestBody MakeTaskInWbsRequest makeTaskInWbsRequest) {
        taskService.makeTaskInWbs(makeTaskInWbsRequest);
    }


    @PostMapping("/branch/{taskId}")
    public void registerBranch(@RequestBody RegisterBranchRequest registerBranchRequest, @PathVariable String taskId) {
        taskService.registerBranch(registerBranchRequest, taskId);
    }
}
