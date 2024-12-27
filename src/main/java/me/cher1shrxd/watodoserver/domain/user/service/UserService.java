package me.cher1shrxd.watodoserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectMemberEntity;
import me.cher1shrxd.watodoserver.domain.user.dto.request.UserUpdateRequest;
import me.cher1shrxd.watodoserver.domain.project.dto.response.ProjectResponse;
import me.cher1shrxd.watodoserver.domain.user.dto.response.UserResponse;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserResponse getMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        List<ProjectResponse> projectResponses = userEntity.getProjects().stream()
                .map(ProjectMemberEntity::getProject)
                .map(ProjectResponse::of)
                .collect(Collectors.toList());

        return UserResponse.from(userEntity, projectResponses);
    }

    public UserResponse updateMe(UserUpdateRequest userUpdateRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if(userUpdateRequest.currentPassword() == null || !bCryptPasswordEncoder.matches(userUpdateRequest.currentPassword(), userEntity.getPassword())) {
            throw new CustomException(CustomErrorCode.WRONG_PASSWORD);
        }

        if (userUpdateRequest.username() != null) userEntity.setUsername(userUpdateRequest.username());
        if (userUpdateRequest.nickname() != null) userEntity.setNickname(userUpdateRequest.nickname());
        if (userUpdateRequest.password() != null) {
            String hashedPassword = bCryptPasswordEncoder.encode(userUpdateRequest.password());
            userEntity.setPassword(hashedPassword);
        }
        if (userUpdateRequest.pat() != null) {
            boolean isExistPat =  userRepository.existsByPat(userUpdateRequest.pat());

            if (isExistPat) {
                throw new CustomException(CustomErrorCode.PAT_ALREADY_EXIST);
            }

            userEntity.setPat(userUpdateRequest.pat());
        }

        userRepository.save(userEntity);

        List<ProjectResponse> projectResponses = userEntity.getProjects().stream()
                .map(ProjectMemberEntity::getProject)
                .map(ProjectResponse::of)
                .collect(Collectors.toList());

        return UserResponse.from(userEntity, projectResponses);
    }
}
