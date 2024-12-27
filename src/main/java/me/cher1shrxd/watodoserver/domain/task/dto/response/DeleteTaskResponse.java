package me.cher1shrxd.watodoserver.domain.task.dto.response;

import me.cher1shrxd.watodoserver.domain.sprint.dto.response.SprintResponse;
import me.cher1shrxd.watodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.watodoserver.domain.wbs.dto.response.WbsResponse;
import me.cher1shrxd.watodoserver.domain.wbs.entity.WbsEntity;

public record DeleteTaskResponse(
        WbsResponse wbs,
        SprintResponse sprint
) {
    public static DeleteTaskResponse of(SprintEntity sprint, WbsEntity wbs) {

        if (sprint == null) {
            WbsResponse wbsResponse = WbsResponse.of(wbs);
            return new DeleteTaskResponse(wbsResponse, null);
        }else{
            SprintResponse sprintResponse = SprintResponse.of(sprint);
            return new DeleteTaskResponse(null, sprintResponse);
        }

    }
}
