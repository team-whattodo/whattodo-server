package me.cher1shrxd.whattodoserver.domain.project.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.project.dto.request.MakeProjectRequest;
import me.cher1shrxd.whattodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.whattodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.whattodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.whattodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.whattodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.whattodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void makeProject(MakeProjectRequest makeProjectRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String repoName = makeProjectRequest.repository().substring(19);
        System.out.println(repoName);

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = ProjectEntity.builder()
                .title(makeProjectRequest.title())
                .detail(makeProjectRequest.detail())
                .repository(repoName)
                .members(List.of(userEntity))
                .build();

        projectRepository.save(projectEntity);
    }
}
