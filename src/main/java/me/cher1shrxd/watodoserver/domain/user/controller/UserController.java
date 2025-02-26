package me.cher1shrxd.watodoserver.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.user.dto.request.UserUpdateRequest;
import me.cher1shrxd.watodoserver.domain.user.dto.response.UserResponse;
import me.cher1shrxd.watodoserver.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;


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

    @PatchMapping("/me")
    public UserResponse updateMe(@RequestBody UserUpdateRequest userUpdateRequest) {
        System.out.println(userUpdateRequest);
        return userService.updateMe(userUpdateRequest);
    }
}
