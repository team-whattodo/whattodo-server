package me.cher1shrxd.watodoserver.domain.github.dto.request;

public record GithubRef(
        String ref,
        GithubRepo repo
) {
}
