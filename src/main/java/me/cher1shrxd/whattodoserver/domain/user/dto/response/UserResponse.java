package me.cher1shrxd.whattodoserver.domain.user.dto.response;

import lombok.*;
import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.whattodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.whattodoserver.domain.user.enums.UserRole;
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

    public UserResponse toUser(UserEntity userEntity){
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .nickname(userEntity.getNickname())
                .build();
    }
}
