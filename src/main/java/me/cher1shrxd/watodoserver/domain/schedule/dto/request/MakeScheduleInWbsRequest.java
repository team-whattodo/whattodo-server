package me.cher1shrxd.watodoserver.domain.schedule.dto.request;

public record MakeScheduleInWbsRequest(
        String title,
        String detail,
        String start,
        String deadline,
        String parentId
) {
}
