package me.cher1shrxd.watodoserver.domain.wbs.dto.request;

public record MaksWbsRequest(
        String title,
        String detail,
        String parentId
) {
}
