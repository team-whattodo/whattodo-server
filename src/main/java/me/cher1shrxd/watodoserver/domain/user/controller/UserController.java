package me.cher1shrxd.watodoserver.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.user.dto.request.UpdateRequest;
import me.cher1shrxd.watodoserver.domain.user.dto.response.ProjectResponse;
import me.cher1shrxd.watodoserver.domain.user.dto.response.UserResponse;
import me.cher1shrxd.watodoserver.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "USER")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getMe() {
        return userService.getMe();
    }

    @GetMapping("/me/projects")
    public List<ProjectResponse> getMyProjects() {
        return userService.getMyProjects();
    }

    @PatchMapping("/me")
    public UserResponse updateMe(@RequestBody UpdateRequest updateRequest) {
        System.out.println(updateRequest);
        return userService.updateMe(updateRequest);
    }
}
