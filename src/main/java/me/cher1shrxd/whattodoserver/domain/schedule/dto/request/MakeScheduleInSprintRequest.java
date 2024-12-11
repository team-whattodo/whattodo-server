package me.cher1shrxd.whattodoserver.domain.schedule.dto.request;

public record MakeScheduleInSprintRequest(
    String title,
    String detail,
    String parentId
) {
}
