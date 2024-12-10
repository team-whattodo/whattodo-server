package me.cher1shrxd.whattodoserver.domain.github.dto.request;

public record GithubWebhookPayload(
        String action,
        PullRequest pull_request
) {

}