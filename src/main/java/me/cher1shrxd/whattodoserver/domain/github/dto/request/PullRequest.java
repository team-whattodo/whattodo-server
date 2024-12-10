package me.cher1shrxd.whattodoserver.domain.github.dto.request;

public record PullRequest(
        String title,
        PullRequestMergedBy merged_by,
        boolean merged,
        GithubRef head
) {

}