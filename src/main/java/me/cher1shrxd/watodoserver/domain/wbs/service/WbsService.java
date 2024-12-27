package me.cher1shrxd.watodoserver.domain.wbs.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.project.entity.ProjectEntity;
import me.cher1shrxd.watodoserver.domain.project.repository.ProjectRepository;
import me.cher1shrxd.watodoserver.domain.user.entity.UserEntity;
import me.cher1shrxd.watodoserver.domain.user.repository.UserRepository;
import me.cher1shrxd.watodoserver.domain.wbs.dto.request.EditWbsRequest;
import me.cher1shrxd.watodoserver.domain.wbs.dto.request.MakeWbsRequest;
import me.cher1shrxd.watodoserver.domain.wbs.dto.response.WbsResponse;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;
import me.cher1shrxd.watodoserver.domain.wbs.repository.WbsRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WbsService {
    private final WbsRepository wbsRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void makeWbs(MakeWbsRequest makeWbsRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        ProjectEntity projectEntity = projectRepository.findById(makeWbsRequest.parentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.PROJECT_NOT_FOUND));

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        WbsEntity wbsEntity = WbsEntity.builder()
                .title(makeWbsRequest.title())
                .detail(makeWbsRequest.detail())
                .project(projectEntity)
                .build();
        wbsRepository.save(wbsEntity);
    }

    public WbsResponse editWbs(EditWbsRequest editWbsRequest, String wbsId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        WbsEntity wbsEntity = wbsRepository.findById(wbsId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = wbsEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        if(editWbsRequest.title() != null) wbsEntity.setTitle(editWbsRequest.title());
        if(editWbsRequest.detail() != null) wbsEntity.setDetail(editWbsRequest.detail());

        wbsRepository.save(wbsEntity);

        return WbsResponse.of(wbsEntity);
    }

    public void deleteWbs(String wbsId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        WbsEntity wbsEntity = wbsRepository.findById(wbsId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = wbsEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getId().equals(userEntity.getId()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        wbsRepository.deleteById(wbsId);
    }

    public WbsResponse getWbs(String wbsId) {
        WbsEntity wbsEntity = wbsRepository.findById(wbsId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.SPRINT_NOT_FOUND));

        ProjectEntity projectEntity = wbsEntity.getProject();

        boolean isMember = projectEntity.getMembers().stream()
                .anyMatch(member -> member.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()));


        if(!isMember) {
            throw new CustomException(CustomErrorCode.NOT_PROJECT_MEMBER);
        }

        return WbsResponse.of(wbsEntity);
    }
}
