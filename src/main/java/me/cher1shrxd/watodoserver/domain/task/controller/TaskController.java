package me.cher1shrxd.watodoserver.domain.task.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.sprint.dto.response.SprintResponse;
import me.cher1shrxd.watodoserver.domain.task.dto.request.*;
import me.cher1shrxd.watodoserver.domain.task.dto.response.TaskResponse;
import me.cher1shrxd.watodoserver.domain.task.service.TaskService;
import me.cher1shrxd.watodoserver.domain.wbs.dto.response.WbsResponse;
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

    @GetMapping("/{taskId}")
    public TaskResponse getTaskDetail(@PathVariable String taskId) {
        return taskService.getTaskDetail(taskId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
    }

    @PatchMapping("/sprint/{taskId}")
    public SprintResponse editTaskInSprint(@RequestBody EditTaskInSprintRequest editTaskInSprintRequest, @PathVariable String taskId) {
        return taskService.editTaskInSprint(editTaskInSprintRequest, taskId);
    }

    @PatchMapping("/wbs/{taskId}")
    public WbsResponse editTaskInWbs(@RequestBody EditTaskInWbsRequest editTaskInWbsRequest, @PathVariable String taskId) {
        return taskService.editTaskInWbs(editTaskInWbsRequest, taskId);
    }
}
