package me.cher1shrxd.whattodoserver.domain.schedule.dto.request;

public record MakeScheduleInWbsRequest(
        String title,
        String detail,
        String start,
        String deadline,
        String parentId
) {
}
