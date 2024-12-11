package me.cher1shrxd.watodoserver.domain.github.dto.request;

public record GithubWebhookPayload(
        String action,
        PullRequest pull_request
) {

}