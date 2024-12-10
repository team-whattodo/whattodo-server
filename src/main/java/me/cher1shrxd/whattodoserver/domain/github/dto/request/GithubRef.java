package me.cher1shrxd.whattodoserver.domain.github.dto.request;

public record GithubRef(
        String label,
        String sha,
        GithubRepo repo
) {
}
