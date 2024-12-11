package me.cher1shrxd.watodoserver.domain.schedule.dto.request;

public record MakeScheduleInSprintRequest(
    String title,
    String detail,
    String parentId
) {
}
