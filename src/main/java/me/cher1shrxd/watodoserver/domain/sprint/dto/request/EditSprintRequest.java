package me.cher1shrxd.watodoserver.domain.sprint.dto.request;

public record EditSprintRequest(
        String title,
        String detail,
        String start,
        String deadline
) {
}
