package me.cher1shrxd.whattodoserver.domain.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.AddSprintRequest;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.AddWbsRequest;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.MakeProjectRequest;
import me.cher1shrxd.whattodoserver.domain.project.service.ProjectService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "PROJECT")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public void makeProject(@RequestBody MakeProjectRequest makeProjectRequest) {
        projectService.makeProject(makeProjectRequest);
    }

    @PostMapping("/{projectId}/sprint")
    public void addSprint(@RequestBody AddSprintRequest addSprintRequest, @PathVariable String projectId) {
        projectService.addSprint(addSprintRequest, projectId);
    }

    @PostMapping("/{projectId}/wbs")
    public void addWbs(@RequestBody AddWbsRequest addWbsRequest, @PathVariable String projectId) {
        projectService.addWbs(addWbsRequest, projectId);
    }

}
