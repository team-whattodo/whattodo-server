package me.cher1shrxd.whattodoserver.domain.schedule.dto.request;

import me.cher1shrxd.whattodoserver.domain.sprint.entity.SprintEntity;
import me.cher1shrxd.whattodoserver.domain.wbs.entity.WbsEntity;

public record MakeScheduleRequest(
    String title,
    String detail,
    String start,
    String deadline,
    SprintEntity sprint,
    WbsEntity wbs
) {
}
