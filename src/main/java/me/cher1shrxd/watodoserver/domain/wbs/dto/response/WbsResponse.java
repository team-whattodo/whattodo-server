package me.cher1shrxd.watodoserver.domain.wbs.dto.response;

import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;

import java.util.List;

public record WbsResponse(
        String id,
        String title,
        String detail,
        List<TaskEntity> task
) {
    public static WbsResponse of(WbsEntity wbsEntity) {
        return new WbsResponse(
                wbsEntity.getId(),
                wbsEntity.getTitle(),
                wbsEntity.getDetail(),
                wbsEntity.getTask()
        );
    }
}
