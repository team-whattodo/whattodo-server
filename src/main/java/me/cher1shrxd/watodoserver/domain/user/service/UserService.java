package me.cher1shrxd.watodoserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectMemberEntity;
import me.cher1shrxd.watodoserver.domain.user.dto.request.UpdateRequest;
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

    public UserResponse updateMe(UpdateRequest updateRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        if(updateRequest.currentPassword() == null || !bCryptPasswordEncoder.matches(updateRequest.currentPassword(), userEntity.getPassword())) {
            throw new CustomException(CustomErrorCode.WRONG_PASSWORD);
        }

        if (updateRequest.username() != null) userEntity.setUsername(updateRequest.username());
        if (updateRequest.nickname() != null) userEntity.setNickname(updateRequest.nickname());
        if (updateRequest.password() != null) {
            String hashedPassword = bCryptPasswordEncoder.encode(updateRequest.password());
            userEntity.setPassword(hashedPassword);
        }
        if (updateRequest.pat() != null) {
            boolean isExistPat =  userRepository.existsByPat(updateRequest.pat());

            if (isExistPat) {
                throw new CustomException(CustomErrorCode.PAT_ALREADY_EXIST);
            }

            userEntity.setPat(updateRequest.pat());
        }

        userRepository.save(userEntity);

        List<ProjectResponse> projectResponses = userEntity.getProjects().stream()
                .map(ProjectMemberEntity::getProject)
                .map(ProjectResponse::of)
                .collect(Collectors.toList());

        return UserResponse.from(userEntity, projectResponses);
    }
}
