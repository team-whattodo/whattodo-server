package me.cher1shrxd.whattodoserver.domain.github.dto.request;

public record GithubRef(
        String ref,
        GithubRepo repo
) {
}
