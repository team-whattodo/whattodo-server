package me.cher1shrxd.watodoserver.domain.github.dto.request;

public record PullRequest(
        String title,
        PullRequestMergedBy merged_by,
        boolean merged,
        GithubRef head
) {

}