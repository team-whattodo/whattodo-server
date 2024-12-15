package me.cher1shrxd.watodoserver.domain.wbs.dto.request;

public record MakeWbsRequest(
        String title,
        String detail,
        String parentId
) {
}
