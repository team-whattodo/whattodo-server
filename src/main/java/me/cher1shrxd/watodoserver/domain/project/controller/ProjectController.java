package me.cher1shrxd.watodoserver.domain.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.dto.request.EditProjectRequest;
import me.cher1shrxd.watodoserver.domain.project.dto.request.MakeProjectRequest;
import me.cher1shrxd.watodoserver.domain.project.dto.response.ProjectDetailResponse;
import me.cher1shrxd.watodoserver.domain.project.service.ProjectService;
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

    @PostMapping("/join/{projectId}")
    public void joinToProject(@PathVariable String projectId) {
        projectService.joinToProject(projectId);
    }

    @GetMapping("/{projectId}")
    public ProjectDetailResponse getProjectDetail(@PathVariable String projectId) {
        return projectService.getProjectDetail(projectId);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
    }

    @DeleteMapping("/leave/{projectId}")
    public void leaveProject(@PathVariable String projectId) {
        projectService.leaveProject(projectId);
    }

    @PatchMapping("/{projectId}")
    public ProjectDetailResponse editProject(@RequestBody EditProjectRequest editProjectRequest, @PathVariable String projectId) {
        return projectService.editProject(editProjectRequest, projectId);
    }

}
