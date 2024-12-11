package me.cher1shrxd.whattodoserver.domain.github.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.github.dto.request.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {

    public ResponseEntity<String> webhookEvent(
            HttpHeaders headers,
            GithubWebhookPayload payload
    ) {
        String eventType = headers.getFirst("X-GitHub-Event");

        if ("pull_request".equals(eventType)) {
            String action = payload.action();

            if ("closed".equals(action) && payload.pull_request().merged()) {
                System.out.println(payload);
                PullRequest pullRequest = payload.pull_request();
                GithubRef ref = pullRequest.head();
                GithubRepo repo = ref.repo();
                String prTitle = pullRequest.title();
                PullRequestMergedBy mergedBy = pullRequest.merged_by();

                System.out.println("Pull Request '" + prTitle + " (" + ref.label() + ")" + " in " + repo.full_name() + "' was merged by " + mergedBy.login());

                return ResponseEntity.ok("Merge event processed");
            }
        }

        return ResponseEntity.ok("Event ignored");
    }
}
