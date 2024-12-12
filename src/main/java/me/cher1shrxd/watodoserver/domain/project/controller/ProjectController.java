package me.cher1shrxd.watodoserver.domain.project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

}
