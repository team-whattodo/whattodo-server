package me.cher1shrxd.watodoserver.domain.project.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.dto.request.EditProjectRequest;
import me.cher1shrxd.watodoserver.domain.project.dto.request.MakeProjectRequest;
import me.cher1shrxd.watodoserver.domain.project.dto.response.ProjectDetailResponse;
import me.cher1shrxd.watodoserver.domain.project.dto.response.ProjectResponse;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectMemberEntity;
import me.cher1shrxd.watodoserver.domain.project.repository.ProjectMemberRepository;
import me.cher1shrxd.watodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectResponse makeProject(MakeProjectRequest makeProjectRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String repoName = makeProjectRequest.repository().substring(19);
        System.out.println(repoName);

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = ProjectEntity.builder()
                .title(makeProjectRequest.title())
                .detail(makeProjectRequest.detail())
                .repository(repoName)
                .build();

        ProjectMemberEntity projectMember = ProjectMemberEntity.builder()
                .user(userEntity)
                .project(projectEntity)
                .build();

        if (projectEntity.getMembers() == null) {
            projectEntity.setMembers(new ArrayList<>());
        }
        projectEntity.getMembers().add(projectMember);

        projectRepository.save(projectEntity);

        return ProjectResponse.of(projectEntity);
    }

    public void joinToProject(String projectId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(isMember){
            throw new CustomException(CustomErrorCode.ALREADY_JOINED_PROJECT);
        }

        ProjectMemberEntity projectMember = ProjectMemberEntity.builder()
                .user(userEntity)
                .project(projectEntity)
                .build();
        projectEntity.getMembers().add(projectMember);

        projectRepository.save(projectEntity);

    }

    public ProjectDetailResponse getProjectDetail(String projectId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));



        if (!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        return ProjectDetailResponse.of(projectEntity);
    }

    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    public void leaveProject(String projectId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        ProjectMemberEntity projectMember = projectEntity.getMembers().stream()
                .filter(member -> member.getUser().getId().equals(userEntity.getId()))
                .findFirst()
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER));

        projectEntity.getMembers().remove(projectMember);

        projectMemberRepository.delete(projectMember);

        projectRepository.save(projectEntity);
    }

    public ProjectDetailResponse editProject(EditProjectRequest editProjectRequest, String projectId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if (!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        if (editProjectRequest.title() != null) projectEntity.setTitle(editProjectRequest.title());
        if (editProjectRequest.detail() != null) projectEntity.setDetail(editProjectRequest.detail());
        if (editProjectRequest.repository() != null) projectEntity.setRepository(editProjectRequest.repository().substring(19));

        projectRepository.save(projectEntity);

        return ProjectDetailResponse.of(projectEntity);
    }

}
