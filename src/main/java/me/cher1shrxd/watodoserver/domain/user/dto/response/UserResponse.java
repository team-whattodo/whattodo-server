package me.cher1shrxd.watodoserver.domain.user.dto.response;

import lombok.*;
import me.cher1shrxd.watodoserver.domain.project.dto.response.ProjectResponse;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private UserRole role;
    private List<ProjectResponse> projects;

    public static UserResponse from(UserEntity userEntity, List<ProjectResponse> projects) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .role(userEntity.getRole())
                .projects(projects)
                .build();
    }
}